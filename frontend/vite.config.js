import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

// https://vitejs.dev/config/
export default defineConfig({
  build:{
    outDir: path.resolve(__dirname, '../src/main/resources/static')
  },
  server: {
    host: true,
    port: 3000,
    proxy: { 
      '/api' : {
        target: 'http://localhost:9000',
        changeOrigin: true //cross origin 허용
      }
    }
  },
  resolve: {
    alias: {
      '@' : path.resolve(__dirname, './src')
    }
  },
  plugins: [vue()]
})