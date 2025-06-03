<script setup>
import {ref} from 'vue'
import axios from 'axios'
import {useCurrencyInput} from 'vue-currency-input'

const id = ref(1)
const timestamp = ref('')
const accountBalance = ref(null)
const toastMessage = ref('')
const showToast = ref(false)

const {inputRef, setValue} = useCurrencyInput({
  currency: 'EUR',
  locale: 'en',
  precision: 2,
  hideCurrencySymbol: false,
})

const showToastMessage = (message) => {
  toastMessage.value = message
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
        id: id.value,
        timestamp: isoTimestamp,
      },
    })
    accountBalance.value = response.data

    if (response.data.name === 'Unknown') {
      showToastMessage('User not found.')
      accountBalance.value = null
      return
    }
    setValue(response.data.balance)
  } catch (error) {
    accountBalance.value = null
    if (error.response?.status === 404 || error.response?.data?.message?.includes('not found')) {
      showToastMessage('User not found.')
    } else {
      showToastMessage('Failed to fetch account balance.')
    }
  }
}
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

    <div class="toast" v-if="showToast">{{ toastMessage }}</div>
  </div>
</template>

<style scoped>
.container {
  max-width: 500px;
  margin: 2rem auto;
  padding: 2rem;
  background: linear-gradient(135deg, #f0f4f8, #ffffff);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
  border-radius: 12px;
  font-family: 'Segoe UI', sans-serif;
  position: relative;
}

h1 {
  text-align: center;
  margin-bottom: 1.5rem;
  color: #333;
}

.form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  margin-bottom: 1.5rem;
}

label {
  font-weight: 500;
  color: #555;
}

input {
  width: 100%;
  padding: 0.6rem;
  margin-top: 0.3rem;
  border: 1px solid #ccc;
  border-radius: 8px;
  font-size: 1rem;
}

button {
  padding: 0.75rem;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #0056b3;
}

.result-card {
  background-color: #e9f7ef;
  border-left: 5px solid #28a745;
  padding: 1rem;
  border-radius: 8px;
}

.balance-display {
  background-color: #f5fff8;
  color: #2e7d32;
  font-weight: bold;
  border: none;
}

.toast {
  position: absolute;
  top: 1rem;
  right: 1rem;
  background-color: #ff5252;
  color: white;
  padding: 0.75rem 1.25rem;
  border-radius: 6px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  animation: fadeInOut 3s ease forwards;
}

@keyframes fadeInOut {
  0% {
    opacity: 0;
    transform: translateY(-10px);
  }
  10% {
    opacity: 1;
    transform: translateY(0);
  }
  90% {
    opacity: 1;
  }
  100% {
    opacity: 0;
    transform: translateY(-10px);
  }
}
</style>
