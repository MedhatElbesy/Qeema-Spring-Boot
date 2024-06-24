<template>
  <div class="card">
    <div class="card-body">
      <h2 class="text-center mb-4">Create Product</h2>
      <form @submit.prevent="createProduct" class="mt-4">
        <div class="mb-3">
          <label for="productName" class="form-label">Name:</label>
          <input type="text" id="productName" v-model="product.name" class="form-control" required>
        </div>
        <div class="mb-3">
          <label for="productPrice" class="form-label">Price:</label>
          <input type="number" id="productPrice" v-model.number="product.price" class="form-control" min="0.01" step="0.01" required>
        </div>
        <div class="mb-3">
          <label for="productQuantity" class="form-label">Quantity:</label>
          <input type="number" id="productQuantity" v-model.number="product.quantity" class="form-control" min="1" required>
        </div>
        <button type="submit" class="btn btn-primary w-100">Create Product</button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from '../axios';

export default {
  data() {
    return {
      product: {
        name: '',
        price: 0.01,
        quantity: 1
      }
    };
  },
  methods: {
    createProduct() {
      axios.post('/products', this.product)
        .then(response => {
          console.log('Product created:', response.data);
          this.$router.push('/dashboard/products');
        })
        .catch(error => {
          console.error('Error creating product:', error);
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

.text-center {
  text-align: center;
}

.mb-4 {
  margin-bottom: 1.5rem;
}

.mt-4 {
  margin-top: 1.5rem;
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
</style>
