<template>
  <div class="container mt-5">
    <div class="row justify-content-center">
      <div class="col-md-6">
        <div class="card">
          <div class="card-header">Вход</div>
          <div class="card-body">
            <form @submit.prevent="loginUser">
            <div class="form-group">
              <label>Email</label>
              <input v-model="user.username" class="form-control" type="email" placeholder="email"/>
              <label>Пароль</label>
              <input v-model="user.password" class="form-control" type="password" placeholder="пароль"/>
            </div>
            <button type="submit" class="btn btn-primary">Отправить</button>
            </form>
            <div class="mt-3"></div>
            <span>Не зарегистрирован? <router-link to="/register">Зарегистрироваться</router-link></span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import AuthService from "../AuthService";
export default {
  data() {
    return {
      user: {
        username: '',
        password: '',
      }
    };
  },
  methods: {
    loginUser() {
      AuthService.login(this.user).then(response => {
        console.log(response.data);
      });
    }
  }
};

</script>

<style scoped>

</style>