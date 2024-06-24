<template>
  <div class="card">
    <div class="card-body text-center">
      <img src="../assets/logo.jpeg" alt="logo" class="w-50 m-auto" />
      <h5 class="card-title text-center">Sign in</h5>
      <form @submit.prevent="login">
        <div class="form-group">
          <input type="email" v-model="email" class="form-control" id="email" placeholder="Email" required />
        </div>
        <div class="form-group my-3">
          <input type="password" v-model="password" class="form-control" id="password" placeholder="Password" required />
        </div>
        
        <button type="submit" class="btn btn-primary btn-block my-2" :disabled="isLoading">
          <span v-if="isLoading" class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
          <span v-if="!isLoading">Sign In</span>
        </button>
        <div class="text-center mt-3">
          <span>Don't have an account? <router-link to="/register">Sign up</router-link></span>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      email: '',
      password: '',
      isLoading: false
    };
  },
  methods: {
    login() {
      this.isLoading = true;
      axios.post('http://localhost:8088/api/v1/auth/signin', {
        email: this.email,
        password: this.password
      })
      .then(response => {
        localStorage.setItem('token', response.data.token);
        localStorage.setItem('role', response.data.role);
        if (response.data.role === 'ADMIN') {
          this.$router.push('/dashboard');
        } else {
          this.$router.push('/products'); 
        }
      })
      .catch(error => {
        console.error('Login error:', error);
        alert('Login failed: ' + (error.response?.data?.message || error.message));
      })
      .finally(() => {
        this.isLoading = false;
      });
    }
  }
};
</script>

<style scoped>
.card {
  max-width: 400px;
  margin: 50px auto;
}

.card-body {
  padding: 20px;
}

.card-title {
  margin-top: 20px;
  margin-bottom: 20px;
}

.btn-primary {
  background: linear-gradient(to right, #73bc60, #609d50);
  border: none;
}

.btn-primary:hover {
  background: linear-gradient(to right, #609d50, #73bc60);
}

form a {
  color: #609d50;
  text-decoration: none;
  font-weight: 500;
}

.form-group {
  margin-bottom: 15px;
}
</style>
