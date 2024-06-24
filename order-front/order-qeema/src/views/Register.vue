<template>
  <div class="card">
    <div class="card-body text-center">
      <img src="../assets/logo.jpeg" alt="logo" class="logo" />
      <h2 class="mb-4">Register</h2>
      <form @submit.prevent="register" class="row g-3 needs-validation" novalidate>
        <div class="col-md-6">
          <label for="firstName" class="form-label">First Name:</label>
          <input type="text" id="firstName" v-model="firstName" class="form-control" required>
          <div class="invalid-feedback">
            Please enter your first name.
          </div>
        </div>
        
        <div class="col-md-6">
          <label for="lastName" class="form-label">Last Name:</label>
          <input type="text" id="lastName" v-model="lastName" class="form-control" required>
          <div class="invalid-feedback">
            Please enter your last name.
          </div>
        </div>
        
        <div class="col-md-6">
          <label for="email" class="form-label">Email:</label>
          <input type="email" id="email" v-model="email" class="form-control" required>
          <div class="invalid-feedback">
            Please enter a valid email address.
          </div>
        </div>
        
        <div class="col-md-6">
          <label for="password" class="form-label">Password:</label>
          <input type="password" id="password" v-model="password" class="form-control" required>
          <div class="invalid-feedback">
            Please enter a password.
          </div>
        </div>
        
        <div class="col-md-6 mx-auto">
          <button type="submit" class="btn btn-primary w-100">Register</button>
        </div>
      </form>
      <p class="mt-3">Already have an account? <router-link to="/login">Login here</router-link>.</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      firstName: '',
      lastName: '',
      email: '',
      password: ''
    };
  },
  methods: {
    register() {
      axios.post('http://localhost:8088/api/v1/auth/signup', {
        firstName: this.firstName,
        lastName: this.lastName,
        email: this.email,
        password: this.password
      })
      .then(response => {
        console.log('Registration successful:', response.data);
        this.$router.push('/login');
      })
      .catch(error => {
        console.error('Registration error:', error);
      });
    }
  }
};
</script>

<style scoped>
.card {
  max-width: 500px;
  margin: 50px auto;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.card-body {
  padding: 20px;
}

.logo {
  width: 50%;
  margin-bottom: 20px;
}

.text-center {
  text-align: center;
}

.mb-4 {
  margin-bottom: 1.5rem;
}

.mt-3 {
  margin-top: 1rem;
}

.form-control {
  margin-bottom: 15px;
}

.btn-primary {
  background: linear-gradient(to right, #73bc60, #609d50);
  border: none;
  color: white;
}

.btn-primary:hover {
  background: linear-gradient(to right, #609d50, #73bc60);
}

.invalid-feedback {
  display: block;
}
</style>
