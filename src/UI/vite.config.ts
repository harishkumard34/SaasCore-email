import { sveltekit } from '@sveltejs/kit/vite';
import { defineConfig } from 'vite';

export default defineConfig({
	plugins: [sveltekit()],
	server: {
	    proxy: {
	        '/v1': {
	            target: 'http://localhost:8080', // Spring Boot server
	            changeOrigin: true,             // Modify the origin header
	            secure: false                   // Allow self-signed certificates
	        },
	    },
	},
});
