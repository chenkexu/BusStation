package com.wxhl.core.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author caibing.zhang
 * @createdate 2010-8-17 下午12:22:19
 * @Description: SharedPreference
 */
public class SharedPreferenceUtil {
	public static final String FILE_NAME = "share_data"; // SharedPreference操作的文件

	/**
	 * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
	 * @param context
	 * @param key
	 * @param object
	 */
	public static void put(Context context, String key, Object object) {

		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();

		if (object instanceof String) {
			editor.putString(key, (String) object);
		} else if (object instanceof Integer) {
			editor.putInt(key, (Integer) object);
		} else if (object instanceof Boolean) {
			editor.putBoolean(key, (Boolean) object);
		} else if (object instanceof Float) {
			editor.putFloat(key, (Float) object);
		} else if (object instanceof Long) {
			editor.putLong(key, (Long) object);
		} else {
			editor.putString(key, object.toString());
		}

		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
	 * 
	 * @param context
	 * @param key
	 * @param defaultObject
	 * @return
	 */
	public static Object get(Context context, String key, Object defaultObject) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);

		if (defaultObject instanceof String || defaultObject==null) {
			return sp.getString(key, (String) defaultObject);
		} else if (defaultObject instanceof Integer) {
			return sp.getInt(key, (Integer) defaultObject);
		} else if (defaultObject instanceof Boolean) {
			return sp.getBoolean(key, (Boolean) defaultObject);
		} else if (defaultObject instanceof Float) {
			return sp.getFloat(key, (Float) defaultObject);
		} else if (defaultObject instanceof Long) {
			return sp.getLong(key, (Long) defaultObject);
		}
		return null;
	}

	/**
	 * 移除某个key值已经对应的值
	 * 
	 * @param context
	 * @param key
	 */
	public static void remove(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.remove(key);
		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 清除所有数据
	 * 
	 * @param context
	 */
	public static void clear(Context context) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.clear();
		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 查询某个key是否已经存在
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean contains(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		return sp.contains(key);
	}

	/**
	 * 返回所有的键值对
	 * 
	 * @param context
	 * @return
	 */
	public static Map<String, ?> getAll(Context context) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		return sp.getAll();
	}

	/**
	 * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
	 */
	private static class SharedPreferencesCompat {
		private static final Method sApplyMethod = findApplyMethod();

		/**
		 * 反射查找apply的方法
		 * @return
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private static Method findApplyMethod() {
			try {
				Class clz = SharedPreferences.Editor.class;
				return clz.getMethod("apply");
			} catch (NoSuchMethodException e) {
			}

			return null;
		}

		/**
		 * 如果找到则使用apply执行，否则使用commit
		 * 
		 * 里面所有的commit操作使用了SharedPreferencesCompat.apply进行了替代，目的是尽可能的使用apply代替commit
		 * 首先说下为什么，因为commit方法是同步的，并且我们很多时候的commit操作都是UI线程中，毕竟是IO操作，尽可能异步；
		 * 所以我们使用apply进行替代，apply异步的进行写入；
		 * @param editor
		 */
		public static void apply(SharedPreferences.Editor editor) {
			try {
				if (sApplyMethod != null) {
					sApplyMethod.invoke(editor);
					return;
				}
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
			editor.commit();
		}
	}

	// /**
	// * @author caibing.zhang
	// * @createdate 2012-8-17 下午12:26:01
	// * @Description: 保存int数值
	// * @param context
	// * @param key
	// * @param value
	// */
	// public static void saveInt(Context context,String key,int value){
	// SharedPreferences.Editor editor = context.getSharedPreferences(
	// SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
	// editor.putInt(key, value);
	// editor.commit();
	// }
	//
	// /**
	// * @author caibing.zhang
	// * @createdate 2012-8-17 下午12:28:46
	// * @Description: 获取保存的int数值
	// * @param context
	// * @param key
	// * @return
	// */
	// public static int getInt(Context context,String key){
	// SharedPreferences shared =
	// context.getSharedPreferences(SHARED_PREFERENCE_NAME,
	// Context.MODE_PRIVATE);
	// int value=shared.getInt(key, 0);
	// return value;
	// }
	//
	// /**
	// * @author caibing.zhang
	// * @createdate 2012-8-17 下午12:26:01
	// * @Description: 保存long数值
	// * @param context
	// * @param key
	// * @param value
	// */
	// public static void saveLong(Context context,String key,long value){
	// SharedPreferences.Editor editor = context.getSharedPreferences(
	// SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
	// editor.putLong(key, value);
	// editor.commit();
	// }
	//
	// /**
	// * @author caibing.zhang
	// * @createdate 2012-8-17 下午12:28:46
	// * @Description: 获取保存的long数值
	// * @param context
	// * @param key
	// * @return
	// */
	// public static long getLong(Context context,String key){
	// SharedPreferences shared =
	// context.getSharedPreferences(SHARED_PREFERENCE_NAME,
	// Context.MODE_PRIVATE);
	// long value=shared.getLong(key, 0L);
	// return value;
	// }
	//
	// /**
	// * @author miaoxin.ye
	// * @createdate 2012-10-13 上午11:50:33
	// * @Description: 保存boolean值
	// * @param context
	// * @param key
	// * @param value
	// */
	// public static void saveBoolean(Context context,String key,boolean value){
	// SharedPreferences.Editor editor = context.getSharedPreferences(
	// SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
	// editor.putBoolean(key, value);
	// editor.commit();
	// }
	//
	// /**
	// * @author miaoxin.ye
	// * @createdate 2012-10-13 上午11:51:40
	// * @Description: 获取boolean值
	// * @param context
	// * @param key
	// * @return
	// */
	// public static boolean getBoolean(Context context,String key){
	// SharedPreferences shared =
	// context.getSharedPreferences(SHARED_PREFERENCE_NAME,
	// Context.MODE_PRIVATE);
	// boolean value=shared.getBoolean(key, false);
	// return value;
	// }
	//
	// /**
	// * @author caibing.zhang
	// * @createdate 2012-8-17 下午12:26:01
	// * @Description: 保存String数值
	// * @param context
	// * @param key
	// * @param value
	// */
	// public static void saveString(Context context,String key,String value){
	// SharedPreferences.Editor editor = context.getSharedPreferences(
	// SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
	// editor.putString(key, value);
	// editor.commit();
	// }
	//
	// /**
	// * @author caibing.zhang
	// * @createdate 2012-8-17 下午12:28:46
	// * @Description: 获取保存的String数值
	// * @param context
	// * @param key
	// * @return
	// */
	// public static String getString(Context context,String key){
	// SharedPreferences shared =
	// context.getSharedPreferences(SHARED_PREFERENCE_NAME,
	// Context.MODE_PRIVATE);
	// String value=shared.getString(key, "");
	// return value;
	// }
}
