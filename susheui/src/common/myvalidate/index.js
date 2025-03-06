
import Validate from './myValidate';

/* istanbul ignore next */
Validate.install = function(Vue) {

    Vue.prototype.myValidator = {

        valid(scope,options) {
            if (scope==null){
                console.log("实例不正确");
                return false;
            }
            return new Validate.MyValidator(scope,options).valid();
        },
        addMethod(name,callback){
            Validate.validateUtil[name]=callback;
        }
    };

    Vue.directive('validate', {

        inserted: function (el, binding, vnode) {

            //console.log("开始插入");
            let instance = vnode.context;
            if (instance && instance.errors == null) {
                console.log("初始化errors")
                instance.errors = {};
            }
            if(!instance.__initValidate){
                Validate.MyValidator.items={};
                Vue.util.defineReactive(instance,"__initValidate",true);
            }
            console.log("instance=",instance)
            let it = {dom: el};
            //console.log("el=",el);
            let eleName = "dom";
            let index = 0;
            if (el.name != null)
                eleName = el.name;
            if (binding.value != null && binding.value.toString.call(binding.value) === '[object Object]')
                it.rule = binding.value;//设置验证规则
            //自定义元素名称
            while (index++) {
                if (Validate.MyValidator.items[eleName] != null)
                    eleName += index;
                break;
            }
            // 设置校验元素
            Validate.MyValidator.items[eleName] = it;

            el.addEventListener('keyup', function (event) {

                let key = el.name;
                console.log("it.rule=", it.rule);
                for (let type in it.rule) {
                    if (type == "messages" || Validate.validateUtil[type] == null)
                        continue;
                    new  Validate.MyValidator(instance).validate.call(instance, el.name, type);
                }

                if (instance.validateOptions.isShowErrors){
                    new Validate.MyValidator(instance).showErrorMsgs(key);
                }


            });

            // el.addEventListener("blur",function (event){
            //     for(let type in it.rule){
            //         if(validateUtil[type]==null)
            //             continue;
            //         Vue.prototype.myValidator.validate(instance,el.name,type)
            //     }
            //     //console.log("失去焦点值=",el.value);
            //     //Vue.prototype.myValidator.testmsg=el.value;
            // });

        }
    })

};

export default Validate;
