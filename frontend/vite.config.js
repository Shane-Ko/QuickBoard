import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// 백엔드(Spring Boot :8080)에 CORS 설정이 없으므로 개발 중에는 프록시로 우회한다.
export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    proxy: {
      '/api': { target: 'http://localhost:8080', changeOrigin: true },
      '/auth': { target: 'http://localhost:8080', changeOrigin: true },
    },
  },
});
