// var webpack = require('webpack');
//vue2
const path = require('path')
const { defineConfig } = require('@vue/cli-service')

function resolve(dir) {
	return path.join(__dirname, dir)
}

function publicPath() {
	if (process.env.NODE_ENV == 'production') {
		return "././";
	} else {
		return "/";
	}
}
// vue.config.js
module.exports = defineConfig({
	// publicPath:"././",
	publicPath: publicPath(),
	// 国际化配置 使用其它语言，默认情况下中文语言包依旧是被引入的

	configureWebpack: {
	    resolve: {
	      fallback: { path: require.resolve("path-browserify") },
	    },
	  },
	lintOnSave: false,
	devServer: {
		open: true,
		host: '0.0.0.0',
		port: 8084,
		hot: true,
		https: false,
		proxy: { // 请求代理服务器
			[process.env.VUE_APP_BASE_API]: {
				target: process.env.VUE_APP_BASE_API_URL,
				changeOrigin: true,
				secure: false,
				pathRewrite: {
					['^' + process.env.VUE_APP_BASE_API]: process.env.VUE_APP_BASR_API_PREFIX
				}
			},
			'/api': {
				target: process.env.VUE_APP_BASE_API_URL,
				changeOrigin: true,
				pathRewrite: {
					'^/api': ''
				}
			}
		},
		allowedHosts: 'all',
		client: {
			webSocketURL: 'auto://0.0.0.0:0/ws'
		}
	},
	chainWebpack(config) {
		config.module
			.rule('svg')
			.exclude.add(resolve('src/icons'))
			.end()
		config.module
			.rule('icons')
			.test(/\.svg$/)
			.include.add(resolve('src/icons'))
			.end()
			.use('svg-sprite-loader')
			.loader('svg-sprite-loader')
			.options({
				symbolId: 'icon-[name]'
			})
			.end()
	}
})
