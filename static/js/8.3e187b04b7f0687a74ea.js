webpackJsonp([8],{LrCi:function(a,t,n){"use strict";function e(a){n("nrz6"),n("VkAa")}Object.defineProperty(t,"__esModule",{value:!0});var r=n("vLgD"),i=n("X2Oc"),c=n("Ebwx"),o=(c.a,{components:{star:c.a},data:function(){return{loading:!1,title:"",author:"",img:"",time:"",content:[],orgUrl:"",score:0,hasScored:!1,articleLabel1:"",articleLabel2:""}},mounted:function(){this.$store.commit("SET_RETURN_TEXT","看新闻"),this.$store.commit("SET_RETURN_URL","/tech"),this.getDetail()},methods:{getDetail:function(){var a=this;this.loading=!0,Object(r.a)({url:"http://www.zhiheyikaoqin.cn/article/getArticleContent",method:"post",data:{artId:this.$route.params.id}}).then(function(t){a.loading=!1,a.title=t.data.data.title,a.author=t.data.data.articleSource,a.img=t.data.data.articleImg,a.time=t.data.data.articleDate,a.orgUrl=t.data.data.articleUrl,a.articleLabel1=t.data.data.articleLabel1||"高考",a.articleLabel2=t.data.data.articleLabel2||"观看更多",-1!==t.data.data.articleContent.indexOf("<p>")?a.content=t.data.data.articleContent.replace(/<p>/g,"").split("</p>").map(function(a){return{type:"text",text:a}}):a.content=t.data.data.articleContent,a.initScore()})},initScore:function(){var a=this;Object(r.a)({url:"http://www.zhiheyikaoqin.cn/article/getArticleScore?openid="+this.$store.state.user.openid+"&artId="+this.$route.params.id,method:"get"}).then(function(t){void 0!==t.data.data&&null!==t.data.data&&void 0!==t.data.data.scoreId?(a.hasScored=!0,a.score=t.data.data.score):a.hasScored=!1})},updateScore:function(a,t){var n=this;if(this.hasScored)return void Object(i.a)(this,"已评价过");this.score=a,console.log("sate",a,t),Object(r.a)({url:"http://www.zhiheyikaoqin.cn/article/putScore?openid="+this.$store.state.user.openid+"&artId="+this.$route.params.id+"&score="+a,method:"get"}).then(function(a){0===a.data.code&&(n.hasScored=!0),Object(i.b)(n,a.data.msg)})}}}),d=function(){var a=this,t=a.$createElement,n=a._self._c||t;return n("div",{staticClass:"article-block"},[n("div",{staticClass:"art-header"},[n("div",{staticClass:"title"},[a._v(a._s(a.title))]),a._v(" "),n("div",{staticClass:"author"},[n("span"),a._v(" "+a._s(a.author))]),a._v(" "),n("div",{staticClass:"time"},[n("span",[a._v(a._s(a.time))])]),a._v(" "),n("div",{staticClass:"tag"},[void 0!==a.articleLabel1&&""!==a.articleLabel1?n("span",[a._v(a._s(a.articleLabel1))]):a._e(),a._v(" "),void 0!==a.articleLabel2&&""!==a.articleLabel2?n("span",[a._v(a._s(a.articleLabel2))]):a._e()])]),a._v(" "),n("div",{staticClass:"art-body"},[void 0!==a.img&&""!==a.img?n("img",{attrs:{src:a.img}}):a._e(),a._v(" "),a._l(a.content,function(t){return["title"===t.type?n("h4",[a._v(a._s(t.text))]):"text"===t.type?n("p",[a._v(a._s(t.text))]):n("img",{attrs:{src:t.text}})]}),a._v(" "),n("div",{staticClass:"rank"},[n("div",{staticClass:"rank-label"},[a._v("评价新闻：")]),a._v(" "),n("div",{staticClass:"rank-content"},[n("star",{attrs:{length:5,value:a.score,isreadonly:a.hasScored},on:{updateScore:a.updateScore}})],1)]),a._v(" "),n("div",{staticClass:"art-org"},[n("a",{attrs:{href:a.orgUrl}},[a._v("原文出处")])])],2)])},s=[],l={render:d,staticRenderFns:s},A=l,b=n("VU/8"),p=e,C=b(o,A,!1,p,"data-v-0dcb5c62",null);t.default=C.exports},OXN4:function(a,t,n){t=a.exports=n("FZ+f")(!0),t.push([a.i,"\n.article-block[data-v-0dcb5c62] {\n  background: #fff;\n  padding: 80px 20px 20px 20px;\n}\n.art-header[data-v-0dcb5c62] {\n  margin-bottom: 20px;\n}\n.art-header .title[data-v-0dcb5c62] {\n    font-size: 17px;\n    line-height: 1.4;\n    margin-bottom: 4px;\n    color: #333;\n}\n.art-header .author[data-v-0dcb5c62],\n  .art-header .time[data-v-0dcb5c62] {\n    display: inline-block;\n    margin-right: 10px;\n    color: #576b95;\n}\n.art-header .author span[data-v-0dcb5c62],\n    .art-header .time span[data-v-0dcb5c62] {\n      color: rgba(0, 0, 0, 0.3);\n}\n.art-header .tag[data-v-0dcb5c62] {\n    display: inline-block;\n    color: #aaa;\n}\n.art-header .tag span[data-v-0dcb5c62] {\n      display: inline-block;\n      background: #ababab;\n      color: #fff;\n      padding: 0 6px;\n      border-radius: 6px;\n}\n.art-body[data-v-0dcb5c62] {\n  color: #333;\n  text-align: justify;\n}\n.art-body h4[data-v-0dcb5c62] {\n    font-size: 16px;\n    color: #576b95;\n}\n.art-body p[data-v-0dcb5c62] {\n    line-height: 2;\n    font-size: 16px;\n}\n.art-body img[data-v-0dcb5c62] {\n    width: 100%;\n}\n.art-org[data-v-0dcb5c62] {\n  margin: 20px 0;\n}\n.art-org a[data-v-0dcb5c62] {\n    color: #576b95;\n}\n.rank[data-v-0dcb5c62] {\n  margin-top: 40px;\n}\n.rank .rank-label[data-v-0dcb5c62] {\n    color: #576b95;\n    line-height: 34px;\n    vertical-align: bottom;\n}\n.rank .rank-label[data-v-0dcb5c62], .rank .rank-content[data-v-0dcb5c62] {\n    display: inline-block;\n}\n","",{version:3,sources:["/Users/lh/work/zk/fe/src/news/article.vue"],names:[],mappings:";AACA;EACE,iBAAiB;EACjB,6BAA6B;CAC9B;AACD;EACE,oBAAoB;CACrB;AACD;IACI,gBAAgB;IAChB,iBAAiB;IACjB,mBAAmB;IACnB,YAAY;CACf;AACD;;IAEI,sBAAsB;IACtB,mBAAmB;IACnB,eAAe;CAClB;AACD;;MAEM,0BAA0B;CAC/B;AACD;IACI,sBAAsB;IACtB,YAAY;CACf;AACD;MACM,sBAAsB;MACtB,oBAAoB;MACpB,YAAY;MACZ,eAAe;MACf,mBAAmB;CACxB;AACD;EACE,YAAY;EACZ,oBAAoB;CACrB;AACD;IACI,gBAAgB;IAChB,eAAe;CAClB;AACD;IACI,eAAe;IACf,gBAAgB;CACnB;AACD;IACI,YAAY;CACf;AACD;EACE,eAAe;CAChB;AACD;IACI,eAAe;CAClB;AACD;EACE,iBAAiB;CAClB;AACD;IACI,eAAe;IACf,kBAAkB;IAClB,uBAAuB;CAC1B;AACD;IACI,sBAAsB;CACzB",file:"article.vue",sourcesContent:["\n.article-block[data-v-0dcb5c62] {\n  background: #fff;\n  padding: 80px 20px 20px 20px;\n}\n.art-header[data-v-0dcb5c62] {\n  margin-bottom: 20px;\n}\n.art-header .title[data-v-0dcb5c62] {\n    font-size: 17px;\n    line-height: 1.4;\n    margin-bottom: 4px;\n    color: #333;\n}\n.art-header .author[data-v-0dcb5c62],\n  .art-header .time[data-v-0dcb5c62] {\n    display: inline-block;\n    margin-right: 10px;\n    color: #576b95;\n}\n.art-header .author span[data-v-0dcb5c62],\n    .art-header .time span[data-v-0dcb5c62] {\n      color: rgba(0, 0, 0, 0.3);\n}\n.art-header .tag[data-v-0dcb5c62] {\n    display: inline-block;\n    color: #aaa;\n}\n.art-header .tag span[data-v-0dcb5c62] {\n      display: inline-block;\n      background: #ababab;\n      color: #fff;\n      padding: 0 6px;\n      border-radius: 6px;\n}\n.art-body[data-v-0dcb5c62] {\n  color: #333;\n  text-align: justify;\n}\n.art-body h4[data-v-0dcb5c62] {\n    font-size: 16px;\n    color: #576b95;\n}\n.art-body p[data-v-0dcb5c62] {\n    line-height: 2;\n    font-size: 16px;\n}\n.art-body img[data-v-0dcb5c62] {\n    width: 100%;\n}\n.art-org[data-v-0dcb5c62] {\n  margin: 20px 0;\n}\n.art-org a[data-v-0dcb5c62] {\n    color: #576b95;\n}\n.rank[data-v-0dcb5c62] {\n  margin-top: 40px;\n}\n.rank .rank-label[data-v-0dcb5c62] {\n    color: #576b95;\n    line-height: 34px;\n    vertical-align: bottom;\n}\n.rank .rank-label[data-v-0dcb5c62], .rank .rank-content[data-v-0dcb5c62] {\n    display: inline-block;\n}\n"],sourceRoot:""}])},VkAa:function(a,t,n){var e=n("x3XA");"string"==typeof e&&(e=[[a.i,e,""]]),e.locals&&(a.exports=e.locals);n("rjj0")("c8ee28b8",e,!0,{})},nrz6:function(a,t,n){var e=n("OXN4");"string"==typeof e&&(e=[[a.i,e,""]]),e.locals&&(a.exports=e.locals);n("rjj0")("4a866a84",e,!0,{})},x3XA:function(a,t,n){t=a.exports=n("FZ+f")(!0),t.push([a.i,"\n.rank-content .iconfont {\n  font-size: 24px;\n}\n","",{version:3,sources:["/Users/lh/work/zk/fe/src/news/article.vue"],names:[],mappings:";AACA;EACE,gBAAgB;CACjB",file:"article.vue",sourcesContent:["\n.rank-content .iconfont {\n  font-size: 24px;\n}\n"],sourceRoot:""}])}});
//# sourceMappingURL=8.3e187b04b7f0687a74ea.js.map