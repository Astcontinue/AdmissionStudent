<script setup lang="ts">
import {ref} from 'vue';
import {useTheme} from 'vuetify';

const theme = useTheme();
const form = ref<any>(null);


// 表单数据
const formData = ref({
  studentId: '',
  name: '',
  gender: null,
  phonenumber: '',
  Accounts: '',
  idcard: '',
  photo: '',
  dateOfBirth: '',
  home: '',
  educated: '',
  studentStatus: '',
  remark: ''
});

// 选项列表
const genders = [
  {title: '男', value: 'male'},
  {title: '女', value: 'female'}
];

// 验证规则
const required = (v: string) => !!v || '必填项';
const mobileValid = (v: string) => /^1[3-9]\d{9}$/.test(v) || '手机号格式错误';


// 表单状态
const isSubmitting = ref(false);
const formIsValid = ref(false);

// 提交表单
const submitForm = async () => {
  const {valid} = await form.value.validate();

  if (valid) {
    isSubmitting.value = true;
    try {
      // TODO: 替换为实际API调用
      await new Promise(resolve => setTimeout(resolve, 1000));
      console.log('提交数据:', formData.value);
    } finally {
      isSubmitting.value = false;
    }
  }
};

// 重置表单
const resetForm = () => {
  form.value.reset();
};

// 图片预览处理
const handleFileUpload = (file: File) => {
  if (file) {
    const reader = new FileReader()
    reader.onload = (e) => {
      formData.value.photo = e.target?.result as string
    }
    reader.readAsDataURL(file)
  }
}
</script>

<template>
  <v-container class="pa-4"> <!-- 修改为 pa-4 或其他值以减小左右间距 -->
    <!-- 左侧内容 -->
    <v-row>
      <v-col cols="12" md="8">
        <!-- 我的主体 -->
        <v-card class="mb-4" elevation="2">
          <v-card-title>我的主体</v-card-title>
          <v-card-text>
            当前账号认证主体是 **又<br/>
            4************6
            <div class="mt-4">
              <v-btn color="primary" @click="toggleTheme">变为企业认证</v-btn>
              <v-btn text>变更认证人</v-btn>
            </div>
          </v-card-text>
        </v-card>

        <!-- 我的账号 -->
        <v-card class="mb-4" elevation="2">
          <v-card-title>我的账号</v-card-title>
          <v-card-text>
            <v-row>
              <v-col cols="12" md="3">
                <v-avatar size="80">
                  <img src="https://placehold.co/80x80" alt="用户头像"/>
                </v-avatar>
              </v-col>
              <v-col cols="12" md="9">
                <v-row dense>
                  <v-col cols="12" sm="6">
                    <div>登录名</div>
                    <div>aliyun*****03394</div>
                  </v-col>
                  <v-col cols="12" sm="6">
                    <div>账号ID</div>
                    <div>1984499634763832</div>
                  </v-col>
                  <!-- 其他字段保持类似结构 -->
                </v-row>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>

        <!-- 编辑资料 -->
        <v-card class="mb-4" elevation="2">
          <v-card-title class="bg-primary">
            <span class="text-h3 text-white">编辑资料</span>
            <!-- 头像预览 -->
            <div class="ml-4">
              <v-avatar size="70" class="mr-2">
                <img :src="formData.photo || 'https://placehold.co/40x40'" alt="用户头像"/>
              </v-avatar>

              <!-- 文件输入框 -->
              <v-file-input
                  v-model="formData.photo"
                  accept="image/*"
                  density="compact"
                  hide-input      style="width: 60px;"
                  @change="handleFileUpload"
              ></v-file-input>
            </div>
            <v-spacer></v-spacer>
          </v-card-title>
          <v-card-text>
            <v-form @submit.prevent="submitForm" ref="form" v-model="formIsValid">
              <v-row>
                <!-- 学生ID -->
                <v-col cols="12" md="6">
                  <v-text-field
                      v-model="formData.studentId"
                      label="学生ID"
                      :rules="[required]"
                      variant="underlined"
                      density="comfortable"
                      clearable
                  ></v-text-field>
                </v-col>
                <!-- 姓名 -->
                <v-col cols="12" md="6">
                  <v-text-field
                      v-model="formData.name"
                      label="姓名"
                      :rules="[required]"
                      variant="underlined"
                      density="comfortable"
                      clearable
                  ></v-text-field>
                </v-col>
                <!-- 性别 -->
                <v-col cols="12" md="6">
                  <v-select
                      v-model="formData.gender"
                      :items="genders"
                      label="性别"
                      :rules="[required]"
                      variant="underlined"
                      density="comfortable"
                      clearable
                  ></v-select>
                </v-col>
                <!-- 电话号码 -->
                <v-col cols="12" md="6">
                  <v-text-field
                      v-model="formData.phonenumber"
                      label="电话号码"
                      :rules="[required, mobileValid]"
                      variant="underlined"
                      density="comfortable"
                      clearable
                  ></v-text-field>
                </v-col>
                <!-- 户口所在地 -->
                <v-col cols="12" md="6">
                  <v-text-field
                      v-model="formData.Accounts"
                      label="户口所在地"
                      :rules="[required]"
                      variant="underlined"
                      density="comfortable"
                      clearable
                  ></v-text-field>
                </v-col>
                <!-- 身份证 -->
                <v-col cols="12" md="6">
                  <v-text-field
                      v-model="formData.idcard"
                      label="身份证"
                      :rules="[required]"
                      variant="underlined"
                      density="comfortable"
                      clearable
                  ></v-text-field>
                </v-col>
                <!--                 照片
                                <v-col cols="12" md="6">
                                  <v-file-input
                                      v-model="formData.photo"
                                      label="照片"
                                      :rules="[required]"
                                      variant="underlined"
                                      prepend-inner-icon="mdi-camera"
                                      density="comfortable"
                                      accept="image/*"
                                  ></v-file-input>
                                </v-col>-->
                <!-- 出生日期 -->
                <v-col cols="12" md="6">
                  <v-text-field
                      v-model="formData.dateOfBirth"
                      label="出生日期"
                      :rules="[required]"
                      variant="underlined"
                      density="comfortable"
                      clearable
                  ></v-text-field>
                </v-col>
                <!-- 家庭住址 -->
                <v-col cols="12" md="6">
                  <v-text-field
                      v-model="formData.home"
                      label="家庭住址"
                      :rules="[required]"
                      variant="underlined"
                      density="comfortable"
                      clearable
                  ></v-text-field>
                </v-col>
                <!-- 受教育程度 -->
                <v-col cols="12" md="6">
                  <v-text-field
                      v-model="formData.educated"
                      label="受教育程度"
                      :rules="[required]"
                      variant="underlined"
                      density="comfortable"
                      clearable
                  ></v-text-field>
                </v-col>
                <!-- 学生状态 -->
                <v-col cols="12" md="6">
                  <v-text-field
                      v-model="formData.studentStatus"
                      label="学生状态"
                      :rules="[required]"
                      variant="underlined"
                      density="comfortable"
                      clearable
                  ></v-text-field>
                </v-col>
                <!-- 备注 -->
                <v-col cols="12">
                  <v-textarea
                      v-model="formData.remark"
                      label="备注"
                      :rules="[required]"
                      variant="underlined"
                      density="comfortable"
                      clearable
                  ></v-textarea>
                </v-col>
              </v-row>
            </v-form>
          </v-card-text>
          <v-card-actions class="pa-4 bg-grey-lighten-4">
            <v-btn
                variant="tonal"
                color="success"
                size="large"
                :loading="isSubmitting"
                :disabled="!formIsValid"
                @click="submitForm"
            >
              保存更改
            </v-btn>
            <v-btn
                variant="tonal"
                color="secondary"
                size="large"
                @click="resetForm"
            >
              重置
            </v-btn>
          </v-card-actions>
        </v-card>

        <!-- 身份与权限 -->
        <v-card class="mb-4" elevation="2">
          <v-card-title>身份与权限</v-card-title>
          <v-card-text>
            <v-row>
              <v-col cols="3">
                <div>用户</div>
                <div class="text-h5">1个</div>
              </v-col>
              <v-col cols="3">
                <div>用户组</div>
                <div class="text-h5">0个</div>
              </v-col>
              <v-col cols="3">
                <div>角色</div>
                <div class="text-h5">6个</div>
              </v-col>
              <v-col cols="3">
                <div>自定义策略</div>
                <div class="text-h5">3个</div>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>
      </v-col>

      <!-- 右侧内容 -->
      <v-col cols="12" md="4">
        <!-- 待办事项 -->
        <v-card class="mb-4" elevation="2">
          <v-card-title>待办事项</v-card-title>
          <v-card-text>暂无事项</v-card-text>
        </v-card>

        <!-- 信息与公告 -->
        <v-card elevation="2">
          <v-card-title>信息与公告</v-card-title>
          <v-card-text>
            <v-list>
              <v-list-item v-for="(item, index) in announcements" :key="index">
                <v-list-item-title>{{ item.title }}</v-list-item-title>
                <v-list-item-subtitle>{{ item.date }}</v-list-item-subtitle>
              </v-list-item>
            </v-list>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped>
.text-h5 {
  font-size: 1.5rem;
}

.v-container {
  max-width: 1800px; /* 设置最大宽度 */
  margin: 0 auto; /* 居中对齐 */
}

/* 添加新的样式规则 */
.v-col.md-8 {
  padding-left: 0 !important; /* 移除左边的内边距 */
}
/* 添加新的样式规则 */
.v-card-title .v-avatar {
  border-radius: 50%; /* 确保头像是圆形 */
  background-color: rgb(255, 255, 255) !important; /* 设置背景颜色 */
}

.v-card-title .v-file-input {
  margin-top: -20px; /* 调整文件输入框的位置 */
  color: rgba(255, 255, 255, 0.99) !important; /* 设置文件输入框的颜色 */
}

/* 在style部分添加 */
.v-card-title.bg-primary {
  padding: 16px 24px !important; /* 调整内边距 */
  min-height: 88px; /* 设置最小高度 */
}

/* 在style部分添加 */
.v-card-actions.bg-grey-lighten-4 {
  display: flex !important;
  justify-content: center !important;
  gap: 8px; /* 控制按钮间距 */
}
</style>
