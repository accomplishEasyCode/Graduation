import RouteButton from "./routebutton"
import RouteTempalte from "./routetemplate"

RouteButton.install=function (Vue,options) {
    Vue.component(RouteButton.name,RouteButton);
}
RouteTempalte.install=function (Vue,options) {
    Vue.component(RouteTempalte.name,RouteTempalte);
}
let RouteItems=[
  RouteButton,
  RouteTempalte
];
export default {
    install:function (Vue,options) {
       RouteItems.forEach(c=>c.install(Vue,options));
    },
    ...RouteItems
}
