package com.neutron.deloan.utils

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.neutron.deloan.NApplication
import java.io.*


/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.common.utils
 * @ClassName:      UIUtils
 * @Description:    公共工具类
 * @Author:         Ben
 * @CreateDate:     2019/7/26 14:33
 */
class UIUtils {
    companion object {


        fun openImage(path: String?): Bitmap? {
            var bitmap: Bitmap? = null
            try {
                val bis = BufferedInputStream(FileInputStream(path))
                bitmap = BitmapFactory.decodeStream(bis)
                bis.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return bitmap
        }

        @Throws(FileNotFoundException::class)
        fun loadBitmap(picturePath: String, width: Int, Height: Int): Bitmap? {
            val opt: BitmapFactory.Options = BitmapFactory.Options()
            opt.inJustDecodeBounds = true
            // 获取到这个图片的原始宽度和高度
            val picWidth: Int = opt.outWidth
            val picHeight: Int = opt.outHeight
            // 获取画布中间方框的宽度和高度
            val screenWidth: Int = width
            val screenHeight: Int = Height
            // isSampleSize是表示对图片的缩放程度，比如值为2图片的宽度和高度都变为以前的1/2
            opt.inSampleSize = 1
            // 根据屏的大小和图片大小计算出缩放比例
            if (picWidth > picHeight) {
                if (picWidth > screenWidth) opt.inSampleSize = picWidth / screenWidth
            } else {
                if (picHeight > screenHeight) opt.inSampleSize = picHeight / screenHeight
            }
            // 生成有像素经过缩放了的bitmap
            opt.inJustDecodeBounds = false
            var bitmap: Bitmap = BitmapFactory.decodeFile(picturePath, opt)
            if (bitmap == null) {
                throw FileNotFoundException("Couldn't open $picturePath")
            }
            return bitmap
        }


        /** 禁止 TextView 水平滚动  */
        private fun banTextViewHorizontallyScrolling(view: View) {
            if (view is ViewGroup) {
                for (index in 0 until (view as ViewGroup).getChildCount()) {
                    banTextViewHorizontallyScrolling((view as ViewGroup).getChildAt(index))
                }
            } else if (view is TextView) {
                (view as TextView).setHorizontallyScrolling(false)
            }
        }
        /** Convert a view to bitmap  */
        fun getBitmapFromView(view: View): Bitmap? {
            return try {
                banTextViewHorizontallyScrolling(view)
                view.destroyDrawingCache()
                view.measure(
                    View.MeasureSpec.makeMeasureSpec(
                        0,
                        View.MeasureSpec.UNSPECIFIED
                    ),
                    View.MeasureSpec.makeMeasureSpec(
                        0,
                        View.MeasureSpec.UNSPECIFIED
                    )
                )
                view.layout(0, 0, view.measuredWidth, view.measuredHeight)
                val bitmap = view.drawingCache
                bitmap?.copy(
                    Bitmap.Config.ARGB_8888,
                    false
                )
            } catch (ex: Throwable) {
                Log.d("TAG", "getBitmapFromView: ")
                null
            }
        }


        fun getContext(): Context {
            return NApplication.sContext
        }
        /**
         * 获取资源对象
         */
//        fun getSkinColor(): Int {
//            return Colorful().getPrimaryColor().getColorPack().dark().asInt()
//        }

//        var color_RED         =      ThemeColor. RED                 .getColorPack().dark().asInt()
//        var color_PINK        =      ThemeColor. PINK                  .getColorPack().dark().asInt()
//        var color_PURPLE      =      ThemeColor. PURPLE                 .getColorPack().dark().asInt()
//        var color_DEEP_PURPLE =      ThemeColor. DEEP_PURPLE                .getColorPack().dark().asInt()
//        var color_INDIGO      =      ThemeColor. INDIGO                 .getColorPack().dark().asInt()
//        var color_BLUE        =      ThemeColor. BLUE                  .getColorPack().dark().asInt()
//        var color_LIGHT_BLUE  =      ThemeColor. LIGHT_BLUE                 .getColorPack().dark().asInt()
//        var color_CYAN        =      ThemeColor. CYAN                  .getColorPack().dark().asInt()
//        var color_TEAL        =      ThemeColor. TEAL                  .getColorPack().dark().asInt()
//        var color_GREEN       =      ThemeColor. GREEN                  .getColorPack().dark().asInt()
//        var color_LIGHT_GREEN =      ThemeColor. LIGHT_GREEN                .getColorPack().dark().asInt()
//        var color_LIME        =      ThemeColor. LIME                  .getColorPack().dark().asInt()
//        var color_YELLOW      =      ThemeColor. YELLOW                 .getColorPack().dark().asInt()
//        var color_AMBER       =      ThemeColor. AMBER                  .getColorPack().dark().asInt()
//        var color_ORANGE      =      ThemeColor. ORANGE                 .getColorPack().dark().asInt()
//        var color_DEEP_ORANGE =      ThemeColor. DEEP_ORANGE                .getColorPack().dark().asInt()
//        var color_BROWN       =      ThemeColor. BROWN                  .getColorPack().dark().asInt()
//        var color_GREY        =      ThemeColor. GREY                  .getColorPack().dark().asInt()
//        var color_BLUE_GREY   =      ThemeColor. BLUE_GREY                 .getColorPack().dark().asInt()
//        var color_WHITE       =      ThemeColor. WHITE                  .getColorPack().dark().asInt()
//        var color_BLACK       =      ThemeColor. BLACK                  .getColorPack().dark().asInt()

        /**
         * 获取资源对象
         */
//        fun getSkinArray(): IntArray {
//            val colors = intArrayOf(
//                         color_RED
//                        ,color_PINK
//                        ,color_PURPLE
//                        ,color_DEEP_PURPLE
//                        ,color_INDIGO
//                        ,color_BLUE
//                        ,color_LIGHT_BLUE
//                        ,color_CYAN
//                        ,color_TEAL
//                        ,color_GREEN
//                        ,color_LIGHT_GREEN
//                        ,color_LIME
//                        ,color_YELLOW
//                        ,color_AMBER
//                        ,color_ORANGE
//                        ,color_DEEP_ORANGE
//                        ,color_BROWN
//                        ,color_GREY
//                        ,color_BLUE_GREY
////                        ,color_WHITE
//                        ,color_BLACK
//
//            )
//            return  colors
//        }

//        fun getSkinThem(color:Int): ThemeColorInterface {
//              var them=ThemeColor. BLACK
//            when (color) {
//              color_RED         ->them=ThemeColor. RED
//              color_PINK        ->them=ThemeColor. PINK
//              color_PURPLE      ->them=ThemeColor. PURPLE
//              color_DEEP_PURPLE ->them=ThemeColor. DEEP_PURPLE
//              color_INDIGO      ->them=ThemeColor. INDIGO
//              color_BLUE        ->them=ThemeColor. BLUE
//              color_LIGHT_BLUE  ->them=ThemeColor. LIGHT_BLUE
//              color_CYAN        ->them=ThemeColor. CYAN
//              color_TEAL        ->them=ThemeColor. TEAL
//              color_GREEN       ->them=ThemeColor. GREEN
//              color_LIGHT_GREEN ->them=ThemeColor. LIGHT_GREEN
//              color_LIME        ->them=ThemeColor. LIME
//              color_YELLOW      ->them=ThemeColor. YELLOW
//              color_AMBER       ->them=ThemeColor. AMBER
//              color_ORANGE      ->them=ThemeColor. ORANGE
//              color_DEEP_ORANGE ->them=ThemeColor. DEEP_ORANGE
//              color_BROWN       ->them=ThemeColor. BROWN
//              color_GREY        ->them=ThemeColor. GREY
//              color_BLUE_GREY   ->them=ThemeColor. BLUE_GREY
////              color_WHITE       ->them=ThemeColor. WHITE
//              color_BLACK       ->them=ThemeColor. BLACK
//            }
//            return  them
//        }


        /**
         * 获取资源对象
         */
        fun getResources(): Resources {
            return getContext().resources
        }


        /**
         * 获取屏幕宽度
         *
         * @return
         */
        fun getAndroiodScreenProperty(): Int {
            val wm =
                getContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
            wm.defaultDisplay.getMetrics(dm)
            val width = dm.widthPixels // 屏幕宽度（像素）
            val height = dm.heightPixels // 屏幕高度（像素）
            val density = dm.density // 屏幕密度（0.75 / 1.0 / 1.5）
            val densityDpi = dm.densityDpi // 屏幕密度dpi（120 / 160 / 240）
            // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
            val screenWidth = (width / density).toInt() // 屏幕宽度(dp)
            val screenHeight = (height / density).toInt() // 屏幕高度(dp)
            return width
        }


        /**
         * 获取资源文件字符串
         *
         * @param id
         * @return
         */
        fun getString(id: Int): String {
            return getContext().resources.getString(id)
        }


        /**
         * 获取资源文件字符串
         *
         * @param id
         * @return
         */
        fun getStringArr(id: Int): Array<String> {
            return getResources().getStringArray(id)
        }

        /**
         * 获取颜色
         *
         * @param context 上下文
         * @param resId   资源ID
         */
        fun getColor(@ColorRes resId: Int): Int {
            return ContextCompat.getColor(getContext(), resId)
        }


        /**
         * 获取Drawable
         *
         * @param context 上下文
         * @param resId   资源ID
         */
        fun getDrawable(context: Context, @DrawableRes resId: Int): Drawable {

            return ContextCompat.getDrawable(context, resId)!!
        }

        /**
         * 获取尺寸资源
         *
         * @param context 上下文
         * @param resId   资源ID
         * @return px
         */
        fun getDimen(context: Context?, @DimenRes resId: Int): Float {
            return getResources().getDimension(resId)
        }



        fun activityShot(activity: Activity): Bitmap { /*获取windows中最顶层的view*/
            val view: View = activity.window.decorView
            //允许当前窗口保存缓存信息
            view.isDrawingCacheEnabled = true
            view.buildDrawingCache()
            //获取状态栏高度
            val rect: Rect = Rect()
            view.getWindowVisibleDisplayFrame(rect)
            val statusBarHeight: Int = rect.top
            val windowManager: WindowManager = activity.getWindowManager()
            //获取屏幕宽和高
            val outMetrics: DisplayMetrics = DisplayMetrics()
            windowManager.getDefaultDisplay().getMetrics(outMetrics)
            val width: Int = outMetrics.widthPixels
            val height: Int = outMetrics.heightPixels
            //去掉状态栏
            val bitmap: Bitmap = Bitmap.createBitmap(
                view.getDrawingCache(), 0, statusBarHeight, width,
                height - statusBarHeight
            )
            //销毁缓存信息
            view.destroyDrawingCache()
            view.setDrawingCacheEnabled(false)
            return bitmap
        }




        /**
         *
         * @param context
         * @param imageFile
         * @return content Uri
         */
        fun getImageContentUri(
            context: Context,
            imageFile: File
        ): Uri? {
            val filePath: String = imageFile.getAbsolutePath()
            val cursor: Cursor? = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf<String>(MediaStore.Images.Media._ID),
                MediaStore.Images.Media.DATA + "=? ",
                arrayOf<String>(filePath),
                null
            )
            if (cursor != null && cursor.moveToFirst()) {
                val id: Int =
                    cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
                val baseUri: Uri =
                    Uri.parse("content://media/external/images/media")
                return Uri.withAppendedPath(baseUri, "" + id)
            } else {
                if (imageFile.exists()) {
                    val values: ContentValues = ContentValues()
                    values.put(MediaStore.Images.Media.DATA, filePath)
                    return context.getContentResolver()
                        .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                } else {
                    return null
                }
            }
        }

         fun createColorStateList(
             normal: Int,
             pressed: Int,
             focused: Int,
             unable: Int
         ): ColorStateList? {
            val colors = intArrayOf(pressed, focused, normal, focused, unable, normal)
            val states = arrayOfNulls<IntArray>(6)
            states[0] = intArrayOf(android.R.attr.state_pressed, android.R.attr.state_enabled)
            states[1] = intArrayOf(android.R.attr.state_enabled, android.R.attr.state_focused)
            states[2] = intArrayOf(android.R.attr.state_enabled)
            states[3] = intArrayOf(android.R.attr.state_focused)
            states[4] = intArrayOf(android.R.attr.state_window_focused)
            states[5] = intArrayOf()
            return ColorStateList(states, colors)
        }




    }




}