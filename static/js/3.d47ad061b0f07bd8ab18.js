webpackJsonp([3],{DGz0:function(t,e,s){"use strict";function i(t){s("xhal")}var o=s("vLgD"),a=s("X2Oc"),n=s("XmB9"),r=(n.a,String,Object,{name:"teaReg",mixins:[n.a],data:function(){return{form:{teaCode:"",teaName:"",teaSubject:"",teaPasswd:""}}},props:{type:{type:String,default:"reg"},info:Object},mounted:function(){"reg"!==this.type&&void 0!==this.info&&(this.form.teaCode=this.info.teaCode||"",this.form.teaName=this.info.teaName||"",this.form.teaSubject=this.info.teaSubject||"")},methods:{reg:function(){var t=this;if(this.isValid=!1,this.checkValid("teaCode"),this.checkValid("teaName"),this.checkValid("teaSubject"),this.checkValid("teaPasswd"),this.isValid)return void this.$vux.toast.show({text:"信息不能为空",type:"cancel",width:"140px"});Object(o.a)({url:"http://www.zhiheyikaoqin.cn/user/registerTeaBaseByOpenid",method:"post",data:{teaOpenid:this.openid,teaCode:this.form.teaCode,teaName:this.form.teaName,teaSubject:this.form.teaSubject,teaPasswd:this.form.teaPasswd}}).then(function(e){0!==e.data.code?Object(a.b)(t,e.data.msg,"120px"):(Object(a.b)(t,"提交成功"),t.$store.commit("SET_INFO_OBJ",e.data.data),t.$store.commit("SET_REG_STATUS",!0),setTimeout(function(){t.$store.dispatch("getInfo"),t.$router.push({name:"course"})},500))})}}}),c=function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"register-form tea"},[s("group",[s("x-input",{attrs:{title:"姓名"},model:{value:t.form.teaName,callback:function(e){t.$set(t.form,"teaName",e)},expression:"form.teaName"}}),t._v(" "),s("x-input",{attrs:{title:"工号"},model:{value:t.form.teaCode,callback:function(e){t.$set(t.form,"teaCode",e)},expression:"form.teaCode"}}),t._v(" "),s("x-input",{attrs:{title:"科目"},model:{value:t.form.teaSubject,callback:function(e){t.$set(t.form,"teaSubject",e)},expression:"form.teaSubject"}})],1),t._v(" "),s("group",["reg"===t.type?s("div",{staticClass:"password-control vux-cell-box"},[t.pwdVis?t._e():s("x-input",{attrs:{title:"密码",type:"password"},model:{value:t.form.teaPasswd,callback:function(e){t.$set(t.form,"teaPasswd",e)},expression:"form.teaPasswd"}}),t._v(" "),t.pwdVis?s("x-input",{attrs:{title:"密码",type:"text"},model:{value:t.form.teaPasswd,callback:function(e){t.$set(t.form,"teaPasswd",e)},expression:"form.teaPasswd"}}):t._e(),t._v(" "),t.pwdVis?t._e():s("i",{staticClass:"iconfont icon-mimakejian",on:{click:function(e){t.pwdVisible(!0)}}}),t._v(" "),t.pwdVis?s("i",{staticClass:"iconfont icon-bukejian",on:{click:function(e){t.pwdVisible(!1)}}}):t._e()],1):t._e()]),t._v(" "),"reg"===t.type?s("div",{staticClass:"btn send-btn",on:{click:t.reg}},[t._v("提交")]):t._e()],1)},u=[],l={render:c,staticRenderFns:u},d=l,p=s("VU/8"),f=i,m=p(r,d,!1,f,"data-v-8c2d88e2",null);e.a=m.exports},H7N9:function(t,e,s){e=t.exports=s("FZ+f")(!0),e.push([t.i,"\n.register-form .weui-label {\n  width: 70px !important;\n}\n","",{version:3,sources:["/Users/lh/work/zk/fe/src/register/index.vue"],names:[],mappings:";AACA;EACE,uBAAuB;CACxB",file:"index.vue",sourcesContent:["\n.register-form .weui-label {\n  width: 70px !important;\n}\n"],sourceRoot:""}])},NJZX:function(t,e,s){var i=s("H7N9");"string"==typeof i&&(i=[[t.i,i,""]]),i.locals&&(t.exports=i.locals);s("rjj0")("e32461c2",i,!0,{})},UGtT:function(t,e,s){e=t.exports=s("FZ+f")(!0),e.push([t.i,"\n.password-control[data-v-537e017f] {\n  position: relative;\n  padding-right: 40px;\n}\n.password-control .iconfont[data-v-537e017f] {\n    position: absolute;\n    right: 0;\n    top: 0;\n    height: 22px;\n    padding: 7px 12px;\n}\n","",{version:3,sources:["/Users/lh/work/zk/fe/src/register/stuReg.vue"],names:[],mappings:";AACA;EACE,mBAAmB;EACnB,oBAAoB;CACrB;AACD;IACI,mBAAmB;IACnB,SAAS;IACT,OAAO;IACP,aAAa;IACb,kBAAkB;CACrB",file:"stuReg.vue",sourcesContent:["\n.password-control[data-v-537e017f] {\n  position: relative;\n  padding-right: 40px;\n}\n.password-control .iconfont[data-v-537e017f] {\n    position: absolute;\n    right: 0;\n    top: 0;\n    height: 22px;\n    padding: 7px 12px;\n}\n"],sourceRoot:""}])},XmB9:function(t,e,s){"use strict";var i=s("rHil"),o=s("pDNl"),a=s("7Ah8");e.a={components:{Group:i.a,XInput:o.a,PopupPicker:a.a},data:function(){return{pwdVis:!0,isValid:!1}},computed:{openid:function(){return this.$store.state.user.openid}},methods:{pwdVisible:function(t){this.pwdVis=t},checkValid:function(t){this.isValid=this.isValid||""===this.form[t].trim()}}}},gAZK:function(t,e,s){e=t.exports=s("FZ+f")(!0),e.push([t.i,"\n.password-control[data-v-8c2d88e2] {\n  position: relative;\n  padding-right: 40px;\n}\n.password-control .iconfont[data-v-8c2d88e2] {\n    position: absolute;\n    right: 0;\n    top: 0;\n    height: 22px;\n    padding: 7px 12px;\n}\n","",{version:3,sources:["/Users/lh/work/zk/fe/src/register/teaReg.vue"],names:[],mappings:";AACA;EACE,mBAAmB;EACnB,oBAAoB;CACrB;AACD;IACI,mBAAmB;IACnB,SAAS;IACT,OAAO;IACP,aAAa;IACb,kBAAkB;CACrB",file:"teaReg.vue",sourcesContent:["\n.password-control[data-v-8c2d88e2] {\n  position: relative;\n  padding-right: 40px;\n}\n.password-control .iconfont[data-v-8c2d88e2] {\n    position: absolute;\n    right: 0;\n    top: 0;\n    height: 22px;\n    padding: 7px 12px;\n}\n"],sourceRoot:""}])},t01H:function(t,e,s){"use strict";function i(t){s("yzOo")}var o=s("vLgD"),a=s("X2Oc"),n=s("XmB9"),r=(n.a,String,Object,{name:"stuReg",mixins:[n.a],data:function(){return{form:{stuCode:"",stuName:"",stuLevel:"",stuGrade:"",stuClass:"",stuPasswd:""},grades:["高中","初中"],levels:["2016级","2017级","2018级","2019级"],classes:["1班","2班","3班","4班","5班","6班","7班","8班","9班","10班","11班","12班","13班","14班","15班","16班","17班","18班","19班"]}},props:{type:{type:String,default:"reg"},info:Object},mounted:function(){"reg"!==this.type&&void 0!==this.info&&(this.form.stuCode=this.info.stuCode||"",this.form.stuName=this.info.stuName||"",this.form.stuLevel=this.info.stuLevel||"",this.form.stuGrade=this.info.stuGrade||"",this.form.stuClass=this.info.stuClass||"")},methods:{selGrade:function(t){console.log(t),this.form.stuGrade=t[0]},selLevel:function(t){console.log(t),this.form.stuLevel=t[0]},selClass:function(t){console.log(t),this.form.stuClass=t[0]},reg:function(){var t=this;if(this.isValid=!1,this.checkValid("stuCode"),this.checkValid("stuName"),this.checkValid("stuLevel"),this.checkValid("stuGrade"),this.checkValid("stuClass"),this.checkValid("stuPasswd"),this.isValid)return void this.$vux.toast.show({text:"信息不能为空",type:"cancel",width:"140px"});Object(o.a)({url:"http://www.zhiheyikaoqin.cn/user/registerStuBaseByOpenid",method:"post",data:{stuOpenid:this.openid,stuCode:this.form.stuCode,stuName:this.form.stuName,stuLevel:this.form.stuLevel,stuGrade:this.form.stuGrade,stuClass:this.form.stuClass,stuPasswd:this.form.stuPasswd}}).then(function(e){0!==e.data.code?Object(a.b)(t,e.data.msg,"120px"):(Object(a.b)(t,"提交成功"),t.$store.commit("SET_INFO_OBJ",e.data.data),t.$store.commit("SET_REG_STATUS",!0),setTimeout(function(){t.$store.dispatch("getInfo"),t.$router.push({name:"course"})},500))})}}}),c=function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"register-form stu"},[s("group",[s("x-input",{attrs:{readonly:"reg"!==t.type,disabled:"reg"!==t.type,title:"姓名"},model:{value:t.form.stuName,callback:function(e){t.$set(t.form,"stuName",e)},expression:"form.stuName"}}),t._v(" "),s("x-input",{attrs:{readonly:"reg"!==t.type,title:"学号"},model:{value:t.form.stuCode,callback:function(e){t.$set(t.form,"stuCode",e)},expression:"form.stuCode"}}),t._v(" "),s("popup-picker",{attrs:{readonly:"reg"!==t.type,disabled:"reg"!==t.type,title:"年级",value:[t.form.stuLevel],data:t.levels,columns:1,"value-text-align":"left"},on:{"on-change":t.selLevel}}),t._v(" "),s("popup-picker",{attrs:{readonly:"reg"!==t.type,disabled:"reg"!==t.type,title:"分段",value:[t.form.stuGrade],data:t.grades,columns:1,"value-text-align":"left"},on:{"on-change":t.selGrade}}),t._v(" "),s("popup-picker",{attrs:{readonly:"reg"!==t.type,disabled:"reg"!==t.type,title:"班级",value:[t.form.stuClass],data:t.classes,columns:1,"value-text-align":"left"},on:{"on-change":t.selClass}})],1),t._v(" "),s("group",["reg"===t.type?s("div",{staticClass:"password-control vux-cell-box"},[t.pwdVis?t._e():s("x-input",{attrs:{title:"密码",type:"password"},model:{value:t.form.stuPasswd,callback:function(e){t.$set(t.form,"stuPasswd",e)},expression:"form.stuPasswd"}}),t._v(" "),t.pwdVis?s("x-input",{attrs:{title:"密码",type:"text"},model:{value:t.form.stuPasswd,callback:function(e){t.$set(t.form,"stuPasswd",e)},expression:"form.stuPasswd"}}):t._e(),t._v(" "),t.pwdVis?t._e():s("i",{staticClass:"iconfont icon-mimakejian",on:{click:function(e){t.pwdVisible(!0)}}}),t._v(" "),t.pwdVis?s("i",{staticClass:"iconfont icon-bukejian",on:{click:function(e){t.pwdVisible(!1)}}}):t._e()],1):t._e()]),t._v(" "),"reg"===t.type?s("div",{staticClass:"btn send-btn",on:{click:t.reg}},[t._v("提交")]):t._e()],1)},u=[],l={render:c,staticRenderFns:u},d=l,p=s("VU/8"),f=i,m=p(r,d,!1,f,"data-v-537e017f",null);e.a=m.exports},xeHp:function(t,e,s){"use strict";function i(t){s("NJZX")}Object.defineProperty(e,"__esModule",{value:!0});var o=s("t01H"),a=s("DGz0"),n=(o.a,a.a,{name:"register",components:{stuReg:o.a,teaReg:a.a},data:function(){return{}},computed:{type:function(){var t=this.$store.state.user.userType,e="teacher"===t?" - 老师":"student"===t?" - 学生":"";return this.$store.commit("SET_RETURN_TEXT","补充信息"+e),t}},mounted:function(){this.$store.commit("SET_RETURN_URL","/my")},methods:{}}),r=function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"register"},["student"===t.type?s("stu-reg"):t._e(),t._v(" "),"teacher"===t.type?s("tea-reg"):t._e()],1)},c=[],u={render:r,staticRenderFns:c},l=u,d=s("VU/8"),p=i,f=d(n,l,!1,p,null,null);e.default=f.exports},xhal:function(t,e,s){var i=s("gAZK");"string"==typeof i&&(i=[[t.i,i,""]]),i.locals&&(t.exports=i.locals);s("rjj0")("4274f152",i,!0,{})},yzOo:function(t,e,s){var i=s("UGtT");"string"==typeof i&&(i=[[t.i,i,""]]),i.locals&&(t.exports=i.locals);s("rjj0")("551c8ec1",i,!0,{})}});
//# sourceMappingURL=3.d47ad061b0f07bd8ab18.js.map