<template>
  <div class="dashboard-container">
    <aside class="sidebar border-light">
      <div class="profile-section">
        <img src="../assets/logo.jpeg" alt="Profile Photo" class="profile-photo">
        <h4 class="fw-bold m-2"> Admin</h4>
      </div>
     <div class="admin-new d-flex flex-column justify-content-between align-items-center">
       <ul>
        <li>
          <router-link to="/dashboard/products">All Products</router-link>
        </li>
        <li>
          <router-link to="/dashboard/orders">All Orders</router-link>
        </li>
        <li v-if="isAdmin">
          <router-link to="/dashboard/create-product">Create Product</router-link>
        </li>
      </ul>
        <button @click="logout" class="btn btn-outline-light btn-sm mt-3">Logout</button>
     </div>
    </aside>
    <main class="main-content">
      <router-view></router-view>
    </main>
  </div>
</template>

<script>
export default {
  computed: {
    isAdmin() {
      return localStorage.getItem('role') === 'ADMIN';
    }
  },
  methods: {
    logout() {
      localStorage.removeItem('token');
      localStorage.removeItem('role');

      this.$router.push('/login');
    }
  }
};
</script>

<style scoped>
.dashboard-container {
  display: flex;
  height: 100vh;
}

.sidebar {
  width: 250px;
  height: 160vh;
  background-color:  #609d50; 
  color: #fff;
  padding: 20px;
  box-shadow: 0 2px 5px  #609d50(0, 0, 0, 0.1);
  font-family: 'Arial', sans-serif; 
}

.profile-section {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.profile-photo {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-right: 10px;
}

.sidebar h4 {
  margin-bottom: 20px;
  color: #fff; 
  font-size: 1.2rem;
  font-weight: 700; 
}

.sidebar ul {
  list-style-type: none;
  padding: 0;
}

.sidebar ul li {
  margin-bottom: 10px;
}

.sidebar ul li a {
  display: block;
  padding: 10px;
  text-decoration: none;
  color: #fff; 
  border-radius: 4px;
  transition: background-color 0.3s ease;
}

.sidebar ul li a:hover {
  background-color:  #b7eea9; 
}

.main-content {
  flex: 1;
  padding: 20px;
}
</style>
