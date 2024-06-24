<template>
  <div>
    <h2 class="text-center my-4">Products</h2>
    <div class="products-container">
      <div class="card product-card" v-for="product in filteredProducts" :key="product.id">
        <div class="title bg-success">{{ product.name }}</div>
        <div class="body">
          <p>Price: ${{ product.price }}</p>
          <p>Quantity: {{ product.quantity }}</p>
          <button v-if="!isAdmin && product.quantity > 0" @click="addToOrder(product)" class="btn btn-primary btn-block">Add to Order</button>
        </div>
      </div>
    </div>
    <div v-if="isAdmin" class="text-center my-4">
      <p>Welcome to Admin Dashboard</p>
    </div>
    <div v-else-if="order.length > 0" class="card order-container">
      <div class="card-body">
        <h3>Your Order</h3>
        <ul>
          <li v-for="item in order" :key="item.productId">
            Product ID: {{ item.productId }}, Quantity: {{ item.quantity }}
          </li>
        </ul>
        <p><strong>Total Price: ${{ calculateTotalPrice(order) }}</strong></p>
        <button @click="placeOrder" class="btn btn-success btn-block">Place Order</button>
      </div>
    </div>
    <div v-else class="text-center my-4">
      <p>No products added to order yet.</p>
    </div>
    <div v-if="isLoggedIn && !isAdmin" class="text-center">
      <button @click="logout" class="btn btn-outline-danger btn-sm mt-3">Logout</button>
    </div>
  </div>
</template>


<script>
import axiosInstance from '../axios';

export default {
  data() {
    return {
      products: [],
      order: [],
      isAdmin: false
    };
  },
  created() {
    this.checkLogin();
  },
  computed: {
    filteredProducts() {
      return this.products.filter(product => product.quantity > 0);
    },
    isLoggedIn() {
      const token = localStorage.getItem('token');
      const role = localStorage.getItem('role');
      return token !== null && role !== 'ADMIN';
    }
  },
  methods: {
    checkLogin() {
      const token = localStorage.getItem('token');
      const role = localStorage.getItem('role'); 
      if (token) {
        this.isAdmin = role === 'ADMIN';
        this.fetchProducts(token); 
      } else {
        this.$router.push('/login'); 
      }
    },
    fetchProducts(token) {
      axiosInstance.get('/products', {
        headers: {
          Authorization: `Bearer ${token}`
        }
      })
      .then(response => {
        this.products = response.data;
      })
      .catch(error => {
        console.error('Error fetching products:', error);
        if (error.response && error.response.status === 401) {
          alert('Unauthorized. Please log in again.');
          this.$router.push('/login');
        }
      });
    },
    addToOrder(product) {
      const existingItem = this.order.find(item => item.productId === product.id);
      if (existingItem) {
        existingItem.quantity++;
      } else {
        this.order.push({ productId: product.id, quantity: 1 });
      }
    },
    calculateTotalPrice(order) {
      return order.reduce((total, item) => {
        const product = this.products.find(p => p.id === item.productId);
        if (product) {
          return total + (product.price * item.quantity);
        } else {
          return total;
        }
      }, 0);
    },
    placeOrder() {
      const token = localStorage.getItem('token');
      if (!token) {
        alert('No token found. Please log in.');
        this.$router.push('/login');
        return;
      }

      const userId = 1; 
      axiosInstance.post(`/orders?userId=${userId}`, this.order, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      })
      .then(response => {
        alert('Order placed successfully');
        this.order = [];
      })
      .catch(error => {
        console.error('Error placing order:', error);
        if (error.response && error.response.status === 401) {
          alert('Unauthorized. Please log in again.');
          this.$router.push('/login');
        }
      });
    },
    logout() {
      localStorage.removeItem('token');
      localStorage.removeItem('role');
      this.$router.push('/login');
    }
  }
};
</script>
<style scoped>
.text-center {
  text-align: center;
}

.my-4 {
  margin-top: 1.5rem;
  margin-bottom: 1.5rem;
}

.products-container {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  justify-content: center;
}

.product-card {
  border: 1px solid #ddd;
  border-radius: 8px;
  width: 300px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  margin: 10px;
}

.title {
  border-radius: 8px 8px 0 0;
  background-color: #28a745; 
  color: white;
  padding: 8px;
  text-align: center;
}

.body {
  padding: 16px;
}

.card-body {
  padding: 16px;
}

.btn-block {
  display: block;
  width: 100%;
}

.btn-primary {
  background: linear-gradient(to right, #73bc60, #609d50);
  border: none;
  color: white;
}

.btn-primary:hover {
  background: linear-gradient(to right, #609d50, #73bc60);
}

.btn-success {
  background-color: #28a745;
  border: none;
  color: white;
}

.btn-success:hover {
  background-color: #218838;
}

.order-container {
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
  background-color: #f0f0f0;
}

.order-container ul {
  list-style: none;
  padding: 0;
}

.order-container li {
  margin-bottom: 8px;
}
</style>
