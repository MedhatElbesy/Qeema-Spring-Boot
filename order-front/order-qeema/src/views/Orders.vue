<template>
  <div class="container mt-5">
    <h2 class="text-center my-4">Orders</h2>
    <div v-if="orders.length === 0" class="text-center mt-3">
      <p>No orders found.</p>
    </div>
    <div v-else class="products-container d-flex flex-wrap justify-content-center gap-3">
      <div v-for="orderWithProducts in orders" :key="orderWithProducts.order.id" class="card product-card mt-3 shadow-sm">
        <div class="title bg-success">
          <h5>Order ID: {{ orderWithProducts.order.id }}</h5>
        </div>
        <div class="body">
          <p><strong>User ID:</strong> {{ orderWithProducts.order.userId }}</p>
          <p><strong>Total Amount:</strong> ${{ calculateTotal(orderWithProducts.products) }}</p>
          <ul class="details list-group">
            <li class="list-group-item" v-for="product in orderWithProducts.products" :key="product.id">
              {{ product.name }} - ${{ product.price }} - Quantity: {{ product.quantity }}
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import axios from '../axios';

export default {
  data() {
    return {
      orders: []
    };
  },
  mounted() {
    this.fetchOrders();
  },
  methods: {
    fetchOrders() {
      axios.get('/orders')
        .then(response => {
          console.log('Fetched orders:', response.data);
          this.orders = response.data;
        })
        .catch(error => {
          console.error('Error fetching orders:', error);
        });
    },
    calculateTotal(products) {
      return products.reduce((total, product) => total + product.price * product.quantity, 0);
    }
  }
};
</script>

<style scoped>
.container {
  max-width: 800px;
}

.product-card {
  width: 350px;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.title {
  border-radius: 8px 8px 0 0;
  background-color: #28a745; 
  color: white;
  padding: 10px;
  text-align: center;
}

.body {
  padding: 20px;
}

.details {
  margin-top: 10px;
}

.list-group-item {
  background-color: #f8f9fa;
  border: none;
}
</style>
