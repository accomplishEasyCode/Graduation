import DropdownList from './dropdownlist.vue';
import MySelect from "./MySelect";
import RouteButton from "../myrouter/routebutton";
import RouteTempalte from "../myrouter/routetemplate";

/* istanbul ignore next */
DropdownList.install = function(Vue) {
    Vue.component(DropdownList.name, DropdownList);
};
MySelect.install = function(Vue) {
    Vue.component(MySelect.name,MySelect);
};
let Items=[
    DropdownList,
    MySelect
];
export default {
    install:function (Vue,options) {
        Items.forEach(c=>c.install(Vue,options));
    },
    ...Items
}

