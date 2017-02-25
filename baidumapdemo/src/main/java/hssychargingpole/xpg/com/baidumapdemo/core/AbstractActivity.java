package hssychargingpole.xpg.com.baidumapdemo.core;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.kennyc.view.MultiStateView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wxhl.core.utils.EStyle;
import com.wxhl.core.utils.FileLocalCache;
import com.wxhl.core.utils.L;
import com.wxhl.core.utils.NetWorkUtil;
import com.wxhl.core.utils.http.WxhlHttpClient;
import com.wxhl.core.utils.imageloder.CustomImageLoader;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import hssychargingpole.xpg.com.baidumapdemo.MyApplication;
import hssychargingpole.xpg.com.baidumapdemo.R;
import hssychargingpole.xpg.com.baidumapdemo.api.RequestAPI;

public abstract class AbstractActivity extends BaseActivity {

    protected Fragment mFragmentContent;  //上一个Fragment

    /**
     * 获取项目中Application
     * @return
     */
    public MyApplication getMyApplication(){
        return (MyApplication)getApplication();
    }

    /**
     * 切换Fragment
     * @param to
     */
    protected void switchFragmentContent(int id,Fragment to) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(mFragmentContent!=null){
            if (mFragmentContent != to) {
                if (!to.isAdded()) { // 先判断是否被add过
                    transaction.hide(mFragmentContent).add(id, to); // 隐藏当前的fragment，add下一个到Activity中
                } else {
                    transaction.hide(mFragmentContent).show(to); // 隐藏当前的fragment，显示下一个
                }
            }
        }else{
            transaction.add(id, to);
        }

        /**
         * Can not perform this action after onSaveInstanceState
         * onSaveInstanceState方法是在该Activity即将被销毁前调用，来保存Activity数据的，如果在保存玩状态后
         * 再给它添加Fragment就会出错。解决办法就是把commit（）方法替换成 commitAllowingStateLoss()就行了，其效果是一样的。
         */
        transaction.commitAllowingStateLoss();  //推荐使用此方法，更安全，更方便
        mFragmentContent = to;
    }

    /**
     * 加载图片方法
     * @param imageView
     * @param url
     */
    public void loadImage(ImageView imageView, final String url){
        CustomImageLoader.loadImage(getMyApplication().getImageLoader(),imageView,url);
    }

    /**
     * 加载用户头像
     * @param imageView
     * @param url
     */
    public void loadHeaderImage(ImageView imageView, final String url){
        CustomImageLoader.loadheaderImage(getMyApplication().getImageLoader(),imageView,url);
    }

    /**
     * 异步请求框架
     */
    public abstract class WxhlAsyncTask{
        //是否需要进行网络判断,true判断(默认),false不需要判断
        private boolean isNetWork = true;
        //是否覆盖【显示白色空白界面】mainBody,显示showWaitDialog, true覆盖并显示showWaitDialog,false不覆盖并显示showWaitDialog
        private boolean isCover;

        private String loadingInfo = null;   //加载提示语
        private String url;

//        private TipInfoLayout mTipInfoLayout = AbstractActivity.super.mTipInfoLayout;

        private MultiStateView mMultiStateView = AbstractActivity.super.mMultiStateView;
        public WxhlAsyncTask(){
            this.isCover=true;
        }

        /**
         * @param isCover 是否覆盖mainBody显示showWaitDialog,
         *  true覆盖显示showWaitDialog,false不覆盖显示showWaitDialog，
         */
        public WxhlAsyncTask(boolean isCover){
            this.isCover=isCover;
        }

        /**
         * @param isCover 是否覆盖【显示白色空白界面】mainBody,显示showWaitDialog, true覆盖并显示showWaitDialog,false不覆盖并显示showWaitDialog
         * @param isNetWork 是否需要进行网络判断,true：判断(默认)，false：不需要判断
         */
        public WxhlAsyncTask(boolean isCover,boolean isNetWork){
            this.isCover=isCover;
            this.isNetWork=isNetWork;
        }

        /**
         * 设置 加载 提示语内容
         * @param loadingInfo
         */
        public void setLoadingInfo(String loadingInfo){
            this.loadingInfo = loadingInfo;
        }


        /**
         * 独自设置自己的MultiStateView
         * @return mMultiStateView
         */
        public WxhlAsyncTask setMultiStateView(MultiStateView mMultiStateView){
            if(mMultiStateView != null){
                this.mMultiStateView=mMultiStateView;
            }
            return this;
        }

        /**
         * @author caibing.zhang
         * @createdate 2015年1月17日 下午3:10:26
         * @Description: 网络加载成功
         * @param responseInfo 服务器返回参数
         */
        public abstract void loadSuccess(String responseInfo);

        /**
         * @author caibing.zhang
         * @createdate 2015年1月17日 下午3:10:49
         * @Description: 网络加载失败：异常处理
         */
        public abstract void exception();

        /**
         * @author caibing.zhang
         * @createdate 2015年1月20日 下午9:57:39
         * @Description: post
         * @param url
         * @param params 参数
         */
        public void post(String url,RequestParams params){
            post(true, url, params);
        }

        /**
         * @author caibing.zhang
         * @createdate 2015年1月20日 下午9:57:39
         * @Description: post
         * @param isLoading 是否显示加载对话框
         * @param url
         * @param params 参数
         */
        public void post(boolean isLoading,String url,RequestParams params){

            //没有网络或不需要网络判断
            if(isNetWork && !NetWorkUtil.NETWORK){
//                mTipInfoLayout.setNetworkError();
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
                showSnackbarMessage(EStyle.ALERT, R.string.not_network);
                exception();
                return;
            }

            this.url=url;
            if(isCover && mainBody!=null){
                mainBody.setVisibility(View.GONE);//mainBody不显示
            }

            if(isLoading){ //如果正在加载中
//                showLoadDialog(true);
                if(TextUtils.isEmpty(loadingInfo)){ //如果没有设置了正在加载中的内容
//                    mTipInfoLayout.setLoading();//默认加载内容信息
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                }else{
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                }
            }
            WxhlHttpClient.post(RequestAPI.getAbsoluteUrl(url),
                    setRequestParams(params), jsonHttpResponseHandler);
        }

        /**
         * @author caibing.zhang
         * @createdate 2015年1月20日 下午9:57:39
         * @Description: post
         * @param url
         * @param params 参数
         */
        public WxhlAsyncTask get(String url, RequestParams params){
            get(true,url,params);
            return this;
        }

        /**
         * @author caibing.zhang
         * @createdate 2015年1月20日 下午9:57:39
         * @Description: post
         * @param isLoading 是否显示加载对话框
         * @param url
         * @param params 参数
         */
        public WxhlAsyncTask get(boolean isLoading, String url, RequestParams params){

            //没有网络或不需要网络判断
            if(isNetWork && !NetWorkUtil.NETWORK){
//                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
                showSnackbarMessage(EStyle.ALERT, R.string.not_network);
                exception();
                return this;
            }

            this.url=url;
            if(isCover && mainBody!=null){
                mainBody.setVisibility(View.GONE);
            }

            if(isLoading){
                if(TextUtils.isEmpty(loadingInfo)){
//                    mTipInfoLayout.setLoading();
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                }else{
//                    mTipInfoLayout.setLoading(loadingInfo);
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                }
            }
            WxhlHttpClient.get(RequestAPI.getAbsoluteUrl(url),
                    setRequestParams(params), jsonHttpResponseHandler);
            return this;
        }

        /**
         * 设置统一 公共参数
         * @param params
         */
        private RequestParams setRequestParams(RequestParams params){
            if(params == null){
                params = new RequestParams();

            }
            //在此添加公共参数

            return params;
        }

        /**
         * @author caibing.zhang
         * @createdate 2015年1月16日 下午10:06:21
         * @Description: 判断返回的判断码
         * @param statusCode
         * @return
         */
        private boolean isStatusCode(int statusCode){
            mainBody.setVisibility(View.VISIBLE);
//            dissmissWaitingDialog();
//            mTipInfoLayout.completeLoading();//完成加载
              mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);//设置内容
            if(statusCode==200 || statusCode==201){  //201(已创建)请求成功并且服务器创建了新的资源。
                return true;
            }else{
                int message;
                switch (statusCode) {
                    case 404:
                        message=R.string.status_code_404;
                        break;
                    case 500:
                        message=R.string.status_code_500;
                        break;
                    default:
                        message=R.string.failure;
                        break;
                }
                showSnackbarMessage(EStyle.ALERT, message);
                return false;
            }
        }

        /**
         * @author caibing.zhang
         * @createdate 2015年1月16日 下午11:14:15
         * @Description: 处理异常
         * @param throwable
         */
        private void handleThrowable(WxhlAsyncTask asyncTask,Throwable throwable){
//            dissmissWaitingDialog();
//            mTipInfoLayout.setNetworkError();
            int resStr = R.string.error;
            try {
                String exceptionInfo=analysisException(throwable);
                L.e(exceptionInfo);
                if(exceptionInfo.indexOf("UnknownHostException")!=-1){
                    resStr = R.string.time_out;
                    showSnackbarMessage(EStyle.ALERT, R.string.time_out);
                }else if(exceptionInfo.indexOf("NoDataException")!=-1){
                    resStr = R.string.no_data;
                    showSnackbarMessage(EStyle.CONFIRM, R.string.no_data);
                }else if(exceptionInfo.indexOf("SocketException")!=-1){
                    showSnackbarMessage(EStyle.ALERT, R.string.time_out);
                    resStr = R.string.time_out;
                }else if(exceptionInfo.indexOf("SocketTimeoutException")!=-1){
                    showSnackbarMessage(EStyle.ALERT, R.string.time_out);
                    resStr = R.string.time_out;
                }else if(exceptionInfo.indexOf("ConnectTimeoutException")!=-1){
                    showSnackbarMessage(EStyle.ALERT, R.string.time_out);
                    resStr = R.string.time_out;
                }else if(exceptionInfo.indexOf("HttpResponseException")!=-1){
                    showSnackbarMessage(EStyle.ALERT, R.string.http_response);
                    resStr = R.string.http_response;
                }else {
                    showSnackbarMessage(EStyle.ALERT, R.string.error);
                    resStr = R.string.error;
                }
            } catch (IOException e) {
                e.printStackTrace();
                showSnackbarMessage(EStyle.ALERT, R.string.error);
            }

            if(isCover){
//                mTipInfoLayout.setLoadError(getString(resStr));
            }
            asyncTask.exception();
        }

        /**
         * 将异常信息转化成字符串
         * @param t
         * @return
         * @throws IOException
         */
        private String analysisException(Throwable t) throws IOException{
            if(t == null)
                return null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try{
                t.printStackTrace(new PrintStream(baos));
            }finally{
                baos.close();
            }
            return baos.toString();
        }

        JsonHttpResponseHandler jsonHttpResponseHandler=new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//				super.onSuccess(statusCode, headers, response);
                L.w("--statusCode-->:" + statusCode + ", url-->:" + RequestAPI.getAbsoluteUrl(url) + ", JSONArray response-->:" + response.toString());
                if(isStatusCode(statusCode)){

                    FileLocalCache.saveFile(url, response.toString());
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);//不显示状态，显示内容
                    loadSuccess(response.toString());
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,JSONObject response) {
//				super.onSuccess(statusCode, headers, response);
                L.w("--statusCode-->:" + statusCode + ", url-->:" + RequestAPI.getAbsoluteUrl(url) + ", JSONObject response-->:" + response);
                if(isStatusCode(statusCode)){
                    String responseInfo=response.toString();

                    FileLocalCache.saveFile(url, responseInfo);
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);//不显示状态，显示内容
                    loadSuccess(responseInfo);
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,String responseString) {
//				super.onSuccess(statusCode, headers, responseString);
                L.w("--statusCode-->:" + statusCode + ", url-->:" + RequestAPI.getAbsoluteUrl(url) + ", String responseString-->:" + responseString);
                if(isStatusCode(statusCode)){
                    FileLocalCache.saveFile(url, responseString);
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);//不显示状态，显示内容
                    loadSuccess(responseString);
                }
            }

            @Override
            public void onFailure(int statusCode,Header[] headers,
                                  String responseString, Throwable throwable) {
//				super.onFailure(statusCode, headers, responseString, throwable);
                L.w("--statusCode-->:" + statusCode + ", url-->:" + RequestAPI.getAbsoluteUrl(url) + ", responseString-->:" + responseString);
                handleThrowable(WxhlAsyncTask.this,throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONArray errorResponse) {
//				super.onFailure(statusCode, headers, throwable, errorResponse);
//				showSnackbarMessage(EStyle.ALERT, R.string.error);
                L.w("--statusCode-->:" + statusCode + ", url-->:" + RequestAPI.getAbsoluteUrl(url) + ", JSONArray errorResponse-->");
                handleThrowable(WxhlAsyncTask.this,throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
//				super.onFailure(statusCode, headers, throwable, errorResponse);
//				showSnackbarMessage(EStyle.ALERT, R.string.error);
                L.d("--statusCode-->:" + statusCode + ", url-->:" + RequestAPI.getAbsoluteUrl(url) + ", JSONObject errorResponse-->");

                handleThrowable(WxhlAsyncTask.this, throwable);
            }
        };
    }







}
