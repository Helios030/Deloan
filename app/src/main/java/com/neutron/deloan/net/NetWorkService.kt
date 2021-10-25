package com.neutron.deloan.net



import com.neutron.deloan.bean.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface NetWorkService {


    //查询申请状态
    @POST("api/loan/queryloanstatus")
    suspend fun getRequestState(@Body body: RequestBody): BaseResponse<LoanStatusResult>

    //发送验证码
    @POST("api/register/app/sendSms")
    suspend fun sendSms(@Body body: RequestBody): BaseResponse<LoginResuleResult>

    //短信登录
    @POST("api/login/v2/smsLogin")
    suspend fun smsLogin(@Body body: RequestBody): BaseResponse<SmsLoginResult>

    //    获取
    @POST("api/user/userStatus")
    suspend fun getUserStatus(@Body body: RequestBody): BaseResponse<UserStatusResult>

    //    //获取用户配置
    @POST("api/user/config")
    suspend fun getUserConfig(@Body body: RequestBody): BaseResponse<UserConfigResult>

    @POST("api/query/queryRegion")
    suspend fun getCityById(@Body body: RequestBody): BaseResponse<List<CityBeanResult>?>?

    @POST("api/user/userWork")
    suspend fun uploadUserWorkInfo(@Body body: RequestBody): BaseResponseNoData


    @POST("api/user/userContact")
    suspend fun uploadContactPerson(@Body body: RequestBody): BaseResponseNoData

    @POST("api/query/bankcode")
    suspend fun getBankInfo(@Body body: RequestBody): BaseResponse<List<BankInfoResult>>

    @POST("api/user/userCard")
    suspend fun uploadMoneyCardInfo(@Body body: RequestBody): BaseResponseNoData

    @POST("api/user/idCardOcr")
    suspend fun uploadIDCard(@Body body: MultipartBody): BaseResponse<IDCardInfoResult>


    @POST("api/user/userBase")
    suspend fun uploadUserInfo(@Body body: RequestBody): BaseResponseNoData


    //Advance活体获取license
    @POST("api/user/license")
    suspend fun getAdvancelicense(@Body body: RequestBody): BaseResponse<AdvanceLicenseResult>

    //获取产品列表
    @POST("api/loan/queryproducts2")
    suspend fun getProducts(@Body body: RequestBody): BaseResponse<List<ProductsResult>>

    @POST("/api/user/queryUserwork")
    suspend fun getServiceWorkInfo(@Body body: RequestBody): BaseResponse<SWorkInfoResult>

    //   确认信息
    @POST("api/loan/confirm")
    suspend fun confirmInfo(@Body body: RequestBody): BaseResponse<ConfirmInfoResult>

    //   提交订单
    @POST("api/loan/v2/loanapp")
    suspend fun uploadRequest(@Body body: RequestBody): BaseResponse<RequestOrderResult>

    // 查询联系人
    @POST("/api/user/queryUserContact")
    suspend fun getServiceContactInfo(@Body body: RequestBody): BaseResponse<SContactInfoResult>

    @POST("api/loan/loanappquery")
    suspend fun getOrderList(@Body body: RequestBody): BaseResponse<List<OrderBeanResult>>

    @POST("api/comm/downoknotify")
    suspend fun uploadAppFirst(@Body body: RequestBody): BaseResponseNoData

    @POST("/api/user/queryUsercard")
    suspend fun getServiceMoneyCard(@Body body: RequestBody): BaseResponse<SMoneyCardInfoResult>

    @POST("/api/user/queryUserBase")
    suspend fun getServiceUserInfo(@Body body: RequestBody): BaseResponse<SUserInfoResult>

    @POST("api/loan/getva")
    suspend fun getVa(@Body body: RequestBody): BaseResponse<VABeanResultX>


    //    @Multipart
    @POST("api/loan/repay/vouch")
    suspend fun uploadRepayment(@Body body: MultipartBody): BaseResponseNoData

    @Multipart
    @POST("api/loan/repay/vouch")
    suspend fun uploadRepayment(@Part body: List<MultipartBody.Part>): BaseResponseNoData


    //
    @POST("/api/fetch/user/addressbook")
    suspend fun uploadPhone(@Body body: RequestBody): BaseResponseNoData

    //
    @POST("/api/fetch/user/device")
    suspend fun uploadApp(@Body body: RequestBody): BaseResponseNoData

//    @POST("api/fetch/user/position")
//  suspend  fun  uploadLocation(@Body body: RequestBody): BaseResponse<RequestReturn>

    //
////    http://mockjs.docway.net/mock/1awz6TK9DUm/login/socialLogin
//
//    //第三方登录
//    @POST("api/login/socialLogin")
//   suspend  fun socialLogin(@Body body: RequestBody): BaseResponse<SocialLoginBean>
//


    @POST("api/user/queryuserMedia")
    suspend fun getUploadImg(@Body body: RequestBody): BaseResponse<SImgInfoResult>


//    获取还款方式
    @POST("api/query/bankcode")
    suspend fun getRepayment(@Body body: RequestBody): BaseResponse<List<RepaymentBeanResult>>



    @POST("/api/fetch/user/message")
    suspend fun uploadSMS(@Body body: RequestBody): BaseResponseNoData



    @POST("/api/fetch/user/call")
    suspend fun uploadCall(@Body body: RequestBody): BaseResponseNoData

    //   提交订单 复借
    @POST("/api/loan/re/loanapp")
    suspend fun uploadRERequest(@Body body: RequestBody): BaseResponse<RequestOrderResult>

}