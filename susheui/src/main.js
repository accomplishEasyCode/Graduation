import Vue from 'vue'
import MainFrame from './MainFrame.vue'
import {app as Application} from "./util/app.js"
import router from './util/router'
import store from './util/store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import "./webui/smartbox/box.all.css"
import 'font-awesome/css/font-awesome.css'
import {axios} from './util/axiosboot'
import VueQuillEditor from 'vue-quill-editor'
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'
import 'swiper/dist/css/swiper.css'
import 'font-awesome/css/font-awesome.min.css'
import 'bootstrap/dist/css/bootstrap.min.css'

//
import VueAwesomeSwiper from 'vue-awesome-swiper'
import dwcontrol from './common/index'
import EasySlider from 'vue-easy-slider'
Vue.use(EasySlider)
Vue.prototype.$http = axios ;
Vue.prototype.$app=Application;
Vue.config.productionTip = false;
Vue.use(ElementUI);
Vue.use(dwcontrol);
Vue.use(VueQuillEditor);
Vue.use(VueAwesomeSwiper);
Vue.config.devtools = true;
new Vue({
  router,
  store,
  render: h => h(MainFrame)
}).$mount('#app');
