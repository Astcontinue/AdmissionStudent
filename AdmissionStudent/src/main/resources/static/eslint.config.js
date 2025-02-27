import globals from 'globals';
import pluginJs from '@eslint/js';
import tseslint from 'typescript-eslint';
import pluginVue from 'eslint-plugin-vue';

export default [
  {
    languageOptions: {
      globals: globals.browser,
      parserOptions: {
        parser: '@typescript-eslint/parser'
      }
    }
  },
  pluginJs.configs.recommended,
  ...tseslint.configs.recommended,
  ...pluginVue.configs['flat/essential']
];

/**
 * 总结来说，eslint.config.js 文件用于配置 ESLint，使其能够正确解析和检查项目中的 JavaScript、TypeScript 和 Vue.js 代码，确保代码质量和一致性
 */