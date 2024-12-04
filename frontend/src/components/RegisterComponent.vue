<template>
  <div class="container mt-5">
    <div closs="row justify-content-center">
      <div class="col col-md-6">
        <div class="card">
          <div class="card-header">Регистрация</div>
          <div class="card-body">
            <form @submit.prevent="registerUser">
              <div class="form-group">
                <label>Фамилия</label>
                <input v-model="user.lastName" type="text" class="form-control" placeholder="Фамилия"/>
                <label>Имя</label>
                <input v-model="user.firstName" type="text" class="form-control" placeholder="Имя"/>
                <label>Отчество</label>
                <input v-model="user.patronymic" type="text" class="form-control" placeholder="Отчество"/>
              </div>
              <div class="form-group">
                <label>Email</label>
                <input v-model="user.username" type="email" class="form-control" placeholder="логин"/>
                <label>Пароль</label>
                <input v-model="user.password" type="password" class="form-control" placeholder="пароль"/>
                <label>Подтвердить пароль</label>
                <input v-model="user.confirmPassword" type="password" class="form-control" placeholder="введите пароль еще раз">
              </div>
              <button class="btn btn-primary" type="submit">Отправить</button>
            </form>
            <div class="mt-3">
              <span>Уже зарегистрирован? <router-link to="/login">Войти</router-link></span>
            </div>
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
        lastName: '',
        firstName: '',
        patronymic: '',
        username: '',
        password: '',
        confirmPassword: ''
      }
    };
  },
  methods: {
    registerUser() {
      AuthService.register(this.user).then(response => {
        console.log(response.data);
      });
    }
  }
};
</script>

<style scoped>

</style>