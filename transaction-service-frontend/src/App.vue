<script setup>
import axios from 'axios'
import {useCurrencyInput} from 'vue-currency-input'
import {computed, nextTick, ref} from 'vue'

const id = ref(1)
const timestamp = ref('')
const accountBalance = ref(null)
const transactions = ref([])
const toastMessage = ref('')
const showToast = ref(false)

const {inputRef, setValue} = useCurrencyInput({
  currency: 'EUR',
  locale: 'en',
  precision: 2,
  hideCurrencySymbol: false,
})

const showToastMessage = (message) => {
  toastMessage.value = message || 'An error occurred.'
  showToast.value = true
  setTimeout(() => {
    showToast.value = false
  }, 3000)
}

const fetchAccountBalance = async () => {
  if (timestamp.value) {
    const ts = new Date(timestamp.value)
    if (ts > new Date()) {
      showToastMessage('Timestamp cannot be in the future.')
      return
    }
  }

  try {
    const isoTimestamp = timestamp.value ? new Date(timestamp.value).toISOString() : undefined
    const response = await axios.get('http://localhost:8100/api/v1/account-balance', {
      params: {
        id: id.value || 0,
        timestamp: isoTimestamp,
      },
    })

    accountBalance.value = response.data || null

    if (!response.data || response.data.name === 'Unknown') {
      showToastMessage('User not found.')
      accountBalance.value = null
      transactions.value = []
      return
    }

    await nextTick()
    setValue(response.data.balance || 0)
    await fetchTransactions()
  } catch (error) {
    accountBalance.value = null
    transactions.value = []
    if (error.response?.status === 404 || error.response?.data?.message?.includes('not found')) {
      showToastMessage('User not found.')
    } else {
      showToastMessage('Failed to fetch account balance.')
    }
  }
}

const fetchTransactions = async () => {
  try {
    const params = {accountId: id.value || 0}
    if (timestamp.value) {
      const fromDate = new Date();
      fromDate.setFullYear(fromDate.getFullYear() - 1);
      const toDate = new Date(timestamp.value)
      params.from = fromDate.toISOString()
      params.to = toDate.toISOString()
    }

    const response = await axios.get('http://localhost:8100/api/v1/transactions', {
      params,
    })
    transactions.value = response.data || []
  } catch (error) {
    transactions.value = []
    showToastMessage('Failed to fetch transactions.')
  }
}

const balancesAtTx = computed(() => {
  if (!accountBalance.value || !transactions.value.length) return [];
  let runningBalance = accountBalance.value.balance;
  const out = [];
  for (let i = 0; i < transactions.value.length; i++) {
    out[i] = runningBalance;
    runningBalance -= transactions.value[i].amount;
  }
  return out;
});

</script>

<template>
  <div class="container">
    <h1>💰 Account Balance Viewer</h1>

    <div class="form">
      <label>
        Account ID:
        <input type="number" v-model="id" placeholder="Enter account ID"/>
      </label>

      <label>
        Timestamp (optional):
        <input type="datetime-local" v-model="timestamp"/>
      </label>

      <button @click="fetchAccountBalance">Fetch Balance</button>
    </div>

    <div class="result-card" v-if="accountBalance">
      <h2>Result</h2>
      <p><strong>ID:</strong> {{ accountBalance.id }}</p>
      <p><strong>Name:</strong> {{ accountBalance.name }}</p>
      <p><strong>Balance:</strong>
        <input ref="inputRef" readonly class="balance-display"/>
      </p>
    </div>

    <div class="transactions-card" v-if="transactions.length">
      <h2>Transactions</h2>
      <table>
        <thead>
        <tr>
          <th>ID</th>
          <th>Type</th>
          <th>Amount</th>
          <th>Balance</th>
          <th>Timestamp</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(tx, index) in transactions" :key="tx.id">
          <td>{{ tx.id }}</td>
          <td>{{ tx.type }}</td>
          <td :class="tx.amount >= 0 ? 'amount-positive' : 'amount-negative'">
            {{ tx.amount >= 0 ? '+' : '' }}{{ tx.amount.toFixed(2) }} €
          </td>
          <td>
            {{ balancesAtTx[index].toFixed(2) }} €
          </td>
          <td>{{ new Date(tx.timestamp).toLocaleString() }}</td>
        </tr>
        </tbody>
      </table>
    </div>

    <div class="toast" v-if="showToast">{{ toastMessage }}</div>
  </div>
</template>
