webpackJsonp([7],{LrCi:function(a,t,e){"use strict";function n(a){e("QbDW"),e("Xmef")}Object.defineProperty(t,"__esModule",{value:!0});var d=e("vLgD"),r=e("X2Oc"),i=e("Ebwx"),o=(i.a,{components:{star:i.a},data:function(){return{loading:!1,title:"",author:"",img:"",time:"",content:[],orgUrl:"",score:0,hasScored:!1,articleLabel1:"",articleLabel2:""}},computed:{isUnReg:function(){return void 0===this.$store.state.user.openid||""===this.$store.state.user.openid}},mounted:function(){console.log(this.$store.state.user.openid),this.$store.commit("SET_RETURN_TEXT","看新闻"),this.$store.commit("SET_RETURN_URL","/tech"),this.getDetail()},methods:{getDetail:function(){var a=this;this.loading=!0,Object(d.a)({url:"article/getArticleContent",method:"post",data:{artId:this.$route.params.id,openid:this.$store.state.user.openid}}).then(function(t){a.loading=!1,a.title=t.data.data.title,a.author=t.data.data.articleSource,a.img=t.data.data.articleImg,a.time=t.data.data.articleDate,a.orgUrl=t.data.data.articleUrl,a.articleLabel1=t.data.data.articleLabel1||"高考",a.articleLabel2=t.data.data.articleLabel2||"观看更多",-1!==t.data.data.articleContent.indexOf("<p>")?a.content=t.data.data.articleContent.replace(/<p>/g,"").split("</p>").map(function(a){return{type:"text",text:a}}):a.content=t.data.data.articleContent,a.initScore()})},initScore:function(){var a=this;Object(d.a)({url:"article/getArticleScore?openid="+this.$store.state.user.openid+"&artId="+this.$route.params.id,method:"get"}).then(function(t){void 0!==t.data.data&&null!==t.data.data&&void 0!==t.data.data.scoreId?(a.hasScored=!0,a.score=t.data.data.score):a.hasScored=!1})},updateScore:function(a,t){var e=this;return this.isUnReg?void Object(r.b)(this,"没注册不允许评论"):this.hasScored?void Object(r.b)(this,"已评价过"):(this.score=a,console.log("sate",a,t),void Object(d.a)({url:"article/putScore?openid="+this.$store.state.user.openid+"&artId="+this.$route.params.id+"&score="+a,method:"get"}).then(function(a){0===a.data.code&&(e.hasScored=!0),Object(r.c)(e,a.data.msg)}))}}}),s=function(){var a=this,t=a.$createElement,e=a._self._c||t;return e("div",{staticClass:"article-block"},[e("div",{staticClass:"art-header"},[e("div",{staticClass:"title"},[a._v(a._s(a.title))]),a._v(" "),e("div",{staticClass:"author"},[e("span"),a._v(" "+a._s(a.author))]),a._v(" "),e("div",{staticClass:"time"},[e("span",[a._v(a._s(a.time))])]),a._v(" "),e("div",{staticClass:"tag"},[void 0!==a.articleLabel1&&""!==a.articleLabel1?e("span",[a._v(a._s(a.articleLabel1))]):a._e(),a._v(" "),void 0!==a.articleLabel2&&""!==a.articleLabel2?e("span",[a._v(a._s(a.articleLabel2))]):a._e()])]),a._v(" "),e("div",{staticClass:"art-body"},[void 0!==a.img&&""!==a.img?e("img",{attrs:{src:a.img}}):a._e(),a._v(" "),a._l(a.content,function(t){return["title"===t.type?e("h4",[a._v(a._s(t.text))]):"text"===t.type?e("p",[a._v(a._s(t.text))]):e("img",{attrs:{src:t.text}})]}),a._v(" "),e("div",{staticClass:"rank"},[e("div",{staticClass:"rank-label"},[a._v("评价新闻：")]),a._v(" "),e("div",{staticClass:"rank-content"},[e("star",{attrs:{length:5,value:a.score,disabled:a.isUnReg,isreadonly:a.hasScored},on:{updateScore:a.updateScore}})],1)]),a._v(" "),e("div",{staticClass:"art-org"},[e("a",{attrs:{href:a.orgUrl}},[a._v("原文出处")])])],2)])},l=[],c={render:s,staticRenderFns:l},b=c,A=e("VU/8"),p=n,C=A(o,b,!1,p,"data-v-dd64beba",null);t.default=C.exports},Nh21:function(a,t,e){t=a.exports=e("FZ+f")(!0),t.push([a.i,"\n.article-block[data-v-dd64beba] {\n  background: #fff;\n  padding: 80px 20px 20px 20px;\n}\n.art-header[data-v-dd64beba] {\n  margin-bottom: 20px;\n}\n.art-header .title[data-v-dd64beba] {\n    font-size: 17px;\n    line-height: 1.4;\n    margin-bottom: 4px;\n    color: #333;\n}\n.art-header .author[data-v-dd64beba],\n  .art-header .time[data-v-dd64beba] {\n    display: inline-block;\n    margin-right: 10px;\n    color: #576b95;\n}\n.art-header .author span[data-v-dd64beba],\n    .art-header .time span[data-v-dd64beba] {\n      color: rgba(0, 0, 0, 0.3);\n}\n.art-header .tag[data-v-dd64beba] {\n    display: inline-block;\n    color: #aaa;\n}\n.art-header .tag span[data-v-dd64beba] {\n      display: inline-block;\n      background: #ababab;\n      color: #fff;\n      padding: 0 6px;\n      border-radius: 6px;\n}\n.art-body[data-v-dd64beba] {\n  color: #333;\n  text-align: justify;\n}\n.art-body h4[data-v-dd64beba] {\n    font-size: 16px;\n    color: #576b95;\n}\n.art-body p[data-v-dd64beba] {\n    line-height: 2;\n    font-size: 16px;\n}\n.art-body img[data-v-dd64beba] {\n    width: 100%;\n}\n.art-org[data-v-dd64beba] {\n  margin: 20px 0;\n}\n.art-org a[data-v-dd64beba] {\n    color: #576b95;\n}\n.rank[data-v-dd64beba] {\n  margin-top: 40px;\n}\n.rank .rank-label[data-v-dd64beba] {\n    color: #576b95;\n    line-height: 34px;\n    vertical-align: bottom;\n}\n.rank .rank-label[data-v-dd64beba], .rank .rank-content[data-v-dd64beba] {\n    display: inline-block;\n}\n","",{version:3,sources:["D:/DATA/Vue/sdc-vue/src/news/article.vue"],names:[],mappings:";AACA;EACE,iBAAiB;EACjB,6BAA6B;CAC9B;AACD;EACE,oBAAoB;CACrB;AACD;IACI,gBAAgB;IAChB,iBAAiB;IACjB,mBAAmB;IACnB,YAAY;CACf;AACD;;IAEI,sBAAsB;IACtB,mBAAmB;IACnB,eAAe;CAClB;AACD;;MAEM,0BAA0B;CAC/B;AACD;IACI,sBAAsB;IACtB,YAAY;CACf;AACD;MACM,sBAAsB;MACtB,oBAAoB;MACpB,YAAY;MACZ,eAAe;MACf,mBAAmB;CACxB;AACD;EACE,YAAY;EACZ,oBAAoB;CACrB;AACD;IACI,gBAAgB;IAChB,eAAe;CAClB;AACD;IACI,eAAe;IACf,gBAAgB;CACnB;AACD;IACI,YAAY;CACf;AACD;EACE,eAAe;CAChB;AACD;IACI,eAAe;CAClB;AACD;EACE,iBAAiB;CAClB;AACD;IACI,eAAe;IACf,kBAAkB;IAClB,uBAAuB;CAC1B;AACD;IACI,sBAAsB;CACzB",file:"article.vue",sourcesContent:["\n.article-block[data-v-dd64beba] {\n  background: #fff;\n  padding: 80px 20px 20px 20px;\n}\n.art-header[data-v-dd64beba] {\n  margin-bottom: 20px;\n}\n.art-header .title[data-v-dd64beba] {\n    font-size: 17px;\n    line-height: 1.4;\n    margin-bottom: 4px;\n    color: #333;\n}\n.art-header .author[data-v-dd64beba],\n  .art-header .time[data-v-dd64beba] {\n    display: inline-block;\n    margin-right: 10px;\n    color: #576b95;\n}\n.art-header .author span[data-v-dd64beba],\n    .art-header .time span[data-v-dd64beba] {\n      color: rgba(0, 0, 0, 0.3);\n}\n.art-header .tag[data-v-dd64beba] {\n    display: inline-block;\n    color: #aaa;\n}\n.art-header .tag span[data-v-dd64beba] {\n      display: inline-block;\n      background: #ababab;\n      color: #fff;\n      padding: 0 6px;\n      border-radius: 6px;\n}\n.art-body[data-v-dd64beba] {\n  color: #333;\n  text-align: justify;\n}\n.art-body h4[data-v-dd64beba] {\n    font-size: 16px;\n    color: #576b95;\n}\n.art-body p[data-v-dd64beba] {\n    line-height: 2;\n    font-size: 16px;\n}\n.art-body img[data-v-dd64beba] {\n    width: 100%;\n}\n.art-org[data-v-dd64beba] {\n  margin: 20px 0;\n}\n.art-org a[data-v-dd64beba] {\n    color: #576b95;\n}\n.rank[data-v-dd64beba] {\n  margin-top: 40px;\n}\n.rank .rank-label[data-v-dd64beba] {\n    color: #576b95;\n    line-height: 34px;\n    vertical-align: bottom;\n}\n.rank .rank-label[data-v-dd64beba], .rank .rank-content[data-v-dd64beba] {\n    display: inline-block;\n}\n"],sourceRoot:""}])},QbDW:function(a,t,e){var n=e("Nh21");"string"==typeof n&&(n=[[a.i,n,""]]),n.locals&&(a.exports=n.locals);e("rjj0")("0de006b2",n,!0,{})},T6B6:function(a,t,e){t=a.exports=e("FZ+f")(!0),t.push([a.i,"\n.rank-content .iconfont {\n  font-size: 24px;\n}\n","",{version:3,sources:["D:/DATA/Vue/sdc-vue/src/news/article.vue"],names:[],mappings:";AACA;EACE,gBAAgB;CACjB",file:"article.vue",sourcesContent:["\n.rank-content .iconfont {\n  font-size: 24px;\n}\n"],sourceRoot:""}])},Xmef:function(a,t,e){var n=e("T6B6");"string"==typeof n&&(n=[[a.i,n,""]]),n.locals&&(a.exports=n.locals);e("rjj0")("6cc4bf61",n,!0,{})}});
//# sourceMappingURL=7.97c1bd59ff0a9e1cc20b.js.map