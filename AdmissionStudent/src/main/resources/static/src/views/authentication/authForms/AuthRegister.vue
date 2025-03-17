<script setup lang="ts">
import { useAuthStore } from '@/stores/auth';
import { useUIStore } from '@/stores/ui';
import { ref } from 'vue';
// icons
import { EyeInvisibleOutlined, EyeOutlined } from '@ant-design/icons-vue';

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


// 注册方法
async function register() {
  try {
    // 1. 触发表单验证
    const isValid = await Regform.value.validate();
    if (!isValid) {
      alert('请填写必填字段');
      return;
    }

    // 2. 显示加载状态
    uiStore.isLoading = true;

    // 3. 调用注册接口
    await authStore.register(
        firstname.value,
        lastname.value,
        username.value,
        password.value,
        company.value  // 传递公司字段
    );

    // 4. 注册成功后的跳转（已在 auth.ts 中处理）

  } catch (error: any) {
    // 5. 错误处理
    alert(`注册失败: ${error.message || '未知错误'}`);
  } finally {
    // 6. 关闭加载状态
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
</template>
