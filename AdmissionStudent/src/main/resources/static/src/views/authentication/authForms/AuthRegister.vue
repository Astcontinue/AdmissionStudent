<script setup lang="ts">
import { useAuthStore } from '@/stores/auth';
import { useUIStore } from '@/stores/ui';
import { ref } from 'vue';
// icons
import { EyeInvisibleOutlined, EyeOutlined } from '@ant-design/icons-vue';
import {router} from "@/router";
import {fetchWrapper} from "@/utils/helpers/fetch-wrapper";
const baseUrl = `${import.meta.env.VITE_API_URL}`;

// 响应式数据
const show1 = ref(false);
const password = ref('');
const username = ref('');
const Regform = ref();
const firstname = ref('');
const lastname = ref('');
const company = ref('');

// 初始化 Store
const authStore = useAuthStore();
const uiStore = useUIStore();

// Password validation rules
const passwordRules = ref([
  (v: string) => !!v || 'Password is required',
  (v: string) => v === v.trim() || 'Password cannot start or end with spaces',
  (v: string) => v.length <= 10 || 'Password must be less than 10 characters'
]);
const firstRules = ref([(v: string) => !!v || 'First Name is required']);
const lastRules = ref([(v: string) => !!v || 'Last Name is required']);
// Email validation rules
const usernameRules = ref([
  (v: string) => !!v.trim() || 'UserName is required',
  (v: string) => {
    const trimmedEmail = v.trim();
    return !/\s/.test(trimmedEmail) || 'Username must not contain spaces';
  },
  (v: string) => /.+@.+\..+/.test(v.trim()) || 'Username must be valid'
]);

// 弹窗控制对象
const snackbar = ref({
  visible: false, // ✅ 正确属性名
  message: '',
  color: 'success'
});

async function register() {
  const isValid = await Regform.value.validate();
  if (!isValid) {
    snackbar.value = { visible: true, message: '请填写必填字段', color: 'error' };
    return;
  }

  try {
    uiStore.isLoading = true;
    const response = await fetchWrapper.post(`${baseUrl}/account/register`, {
      firstname: firstname.value,
      lastname: lastname.value,
      username: username.value.trim(),
      password: password.value.trim(),
      company: company.value
    });

    console.log('完整响应:', response); // 调试输出

    if (response.status >= 200 && response.status < 300) {
      const data = response.data; // 假设fetchWrapper返回的数据结构是{data: {...}}
      if (data.code === 1) {
        snackbar.value = {
          visible: true,
          message: '注册成功，即将跳转登录页',
          color: 'success'
        };
        // 修改原跳转代码
        setTimeout(() => {
          router.push('/login1').then(() => {
            console.log('跳转成功');
          }).catch((error) => {
            console.error('跳转失败:', error);
          });
        }, 2000)
      } else {
        snackbar.value = {
          visible: true,
          message: data.msg || '注册失败',
          color: 'error'
        };
      }
    } else {
      snackbar.value = {
        visible: true,
        message: '请求失败，请稍后再试',
        color: 'error'
      };
    }
  } catch (error: any) {
    console.error('Registration Error:', error);
    let message = '网络错误';
    if (error.response?.data?.msg) {
      message = error.response.data.msg;
    }
    snackbar.value = { visible: true, message, color: 'error' };
  } finally {
    uiStore.isLoading = false;
  }
}


</script>

<template>
  <div class="d-flex justify-space-between align-center">
    <h3 class="text-h3 text-center mb-0">Sign up</h3>
    <router-link to="/login1" class="text-primary text-decoration-none">Already have an account?</router-link>
  </div>


  <v-form ref="Regform" lazy-validation action="/dashboards/analytical" class="mt-7 loginForm">
    <v-row class="my-0">
      <v-col cols="12" sm="6" class="py-0">
        <div class="mb-6">
          <v-label>First Name*</v-label>
          <v-text-field
            v-model="firstname"
            :rules="firstRules"
            hide-details="auto"
            required
            variant="outlined"
            class="mt-2"
            color="primary"
            placeholder="John"
          ></v-text-field>
        </div>
      </v-col>


      <v-col cols="12" sm="6" class="py-0">
        <div class="mb-6">
          <v-label>Last Name*</v-label>
          <v-text-field
            v-model="lastname"
            :rules="lastRules"
            hide-details="auto"
            required
            variant="outlined"
            class="mt-2"
            color="primary"
            placeholder="Doe"
          ></v-text-field>
        </div>
      </v-col>
    </v-row>

    <div class="mb-6">
      <v-label>Company</v-label>
      <v-text-field
          v-model="company"
          hide-details="auto"
          variant="outlined"
          class="mt-2"
          color="primary"
          placeholder="Demo Inc."
      ></v-text-field>
    </div>

    <div class="mb-6">
      <v-label>User Name*</v-label>
      <v-text-field
        v-model="username"
        :rules="usernameRules"
        placeholder="demo@company.com"
        class="mt-2"
        required
        hide-details="auto"
        variant="outlined"
        color="primary"
        @input="username"
      ></v-text-field>
    </div>

    <div class="mb-6">
      <v-label>Password</v-label>
      <v-text-field
        v-model="password"
        :rules="passwordRules"
        placeholder="*****"
        required
        variant="outlined"
        color="primary"
        hide-details="auto"
        :type="show1 ? 'text' : 'password'"
        class="mt-2"
        @input="password"
      >
        <template v-slot:append-inner>
          <v-btn color="secondary" icon rounded variant="text">
            <EyeInvisibleOutlined :style="{ color: 'rgb(var(--v-theme-secondary))' }" v-if="show1 == false" @click="show1 = !show1" />
            <EyeOutlined :style="{ color: 'rgb(var(--v-theme-secondary))' }" v-if="show1 == true" @click="show1 = !show1" />
          </v-btn>
        </template>
      </v-text-field>
    </div>

    <div class="d-sm-inline-flex align-center mt-2 mb-7 mb-sm-0 font-weight-bold">
      <h6 class="text-caption">
        By Signing up, you agree to our
        <router-link to="/register" class="text-primary link-hover font-weight-medium">Terms of Service </router-link>
        and
        <router-link to="/register" class="text-primary link-hover font-weight-medium">Privacy Policy</router-link>
      </h6>
    </div>
    <v-btn
        color="primary"
        block class="mt-4"
        variant="flat"
        size="large"
        @click="register"
        :disabled="uiStore.isLoading"
    >{{ uiStore.isLoading ? '注册中...' : 'Create Account'}}</v-btn>
  </v-form>
  <!-- 更美观的提示样式 -->
  <v-snackbar
      v-model="snackbar.visible"
      :color="snackbar.color"
      location="top center"
      elevation="24"
  >
    <template v-slot:actions>
      <v-btn variant="text" @click="snackbar.visible = false">
        <v-icon>mdi-close</v-icon>
      </v-btn>
    </template>
    {{ snackbar.message }}
  </v-snackbar>
</template>
