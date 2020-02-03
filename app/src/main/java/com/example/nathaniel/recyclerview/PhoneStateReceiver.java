package com.example.nathaniel.recyclerview;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.BlockedNumberContract;
import android.provider.ContactsContract;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PhoneStateReceiver extends BroadcastReceiver {

    private String blockingNumber = "+251930536977";
    private List<String> blockedList;
    Context context = null;
    private static final String TAG = "Phone call";
//    private ITelephony telephonyService;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.PHONE_STATE")){
            final String numberCalling = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            blockedList = SecondActivity.blockedList;

        try {
            if (blockedList.contains(numberCalling)){
                declinePhone(context);

        }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        PhoneCallStateListener customPhoneListener = new PhoneCallStateListener(context);
//        telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
//        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        try {
//            Class c = Class.forName(tm.getClass().getName());
//            Method m = c.getDeclaredMethod("getITelephony");
//            m.setAccessible(true);
//            telephonyService = (ITelephony) m.invoke(tm);
//            Bundle bundle = intent.getExtras();
//            String phoneNumber = bundle.getString("incoming_number");
//            Log.d("INCOMING", phoneNumber);
//            if ((phoneNumber != null)) {
//                telephonyService.endCall();
//                Log.d("HANG UP", phoneNumber);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        //        try {
//            Class c = Class.forName(telephony.getClass().getName());
//            Method m = c.getDeclaredMethod("getITelephony");
//            m.setAccessible(true);
//            telephonyService = (ITelephony) m.invoke(telephony);
//            //telephonyService.silenceRinger();
//            telephonyService.endCall();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


//        if (intent.getAction().equals("android.intent.action.PHONE_STATE")){
//            putBlacklist(context,blockingNumber);
//            final String numberCalling = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
//            blockedList = BlackListFragment.blockedList;
//reject call if number is matched to our blocking number
//            if (blockedList.contains(numberCalling)){
////                putBlacklist(context,numberCalling);
//                disconnectPhoneItelephony(context);
//
//            }
//            if (blockingNumber.equals(numberCalling)){
////                putBlacklist(context,numberCalling);
//                Toast.makeText(context,"phone state is working",Toast.LENGTH_SHORT).show();
//                disconnectPhoneItelephony(context);
//
//            }

//            for (String num:blockedNumber) {
//                if(numberCall.equals(num)){
//                    disconnectPhoneItelephony(context);
//                }
//            }


    }
//    else if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
//        Bundle bundle = intent.getExtras();
//        Object messages[] = (Object[]) bundle.get("pdus");
//            SmsMessage smsMessage[] = new SmsMessage[messages.length];
//
//        }
    }

    private void declinePhone(Context context) throws Exception {

        try {

            String serviceManagerName = "android.os.ServiceManager";
            String serviceManagerNativeName = "android.os.ServiceManagerNative";
            String telephonyName = "com.android.internal.telephony.ITelephony";
            Class<?> telephonyClass;
            Class<?> telephonyStubClass;
            Class<?> serviceManagerClass;
            Class<?> serviceManagerNativeClass;
            Method telephonyEndCall;
            Object telephonyObject;
            Object serviceManagerObject;
            telephonyClass = Class.forName(telephonyName);
            telephonyStubClass = telephonyClass.getClasses()[0];
            serviceManagerClass = Class.forName(serviceManagerName);
            serviceManagerNativeClass = Class.forName(serviceManagerNativeName);
            Method getService = // getDefaults[29];
                    serviceManagerClass.getMethod("getService", String.class);
            Method tempInterfaceMethod = serviceManagerNativeClass.getMethod("asInterface", IBinder.class);
            Binder tmpBinder = new Binder();
            tmpBinder.attachInterface(null, "fake");
            serviceManagerObject = tempInterfaceMethod.invoke(null, tmpBinder);
            IBinder retbinder = (IBinder) getService.invoke(serviceManagerObject, "phone");
            Method serviceMethod = telephonyStubClass.getMethod("asInterface", IBinder.class);
            telephonyObject = serviceMethod.invoke(null, retbinder);
            telephonyEndCall = telephonyClass.getMethod("endCall");
            telephonyEndCall.invoke(telephonyObject);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("unable", "msg cant dissconect call....");

        }
    }
//    private void putBlacklist( Context context,String number){
//        ContentResolver contentResolver = context.getContentResolver();
//        ContentValues values = new ContentValues();
//        values.put(BlockedNumberContract.BlockedNumbers.COLUMN_ORIGINAL_NUMBER, number);
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//            Uri uri = contentResolver.insert(BlockedNumberContract.BlockedNumbers.CONTENT_URI, values);
//        }
//    }


//
//
//    // Keep this method as it is
//    @SuppressWarnings({ "rawtypes", "unchecked" })
//    private void disconnectPhoneItelephony(Context context) {
//        try {
//
//            String serviceManagerName = "android.os.ServiceManager";
//            String serviceManagerNativeName = "android.os.ServiceManagerNative";
//            String telephonyName = "com.android.internal.telephony.ITelephony";
//            Class<?> telephonyClass;
//            Class<?> telephonyStubClass;
//            Class<?> serviceManagerClass;
//            Class<?> serviceManagerNativeClass;
//            Method telephonyEndCall;
//            Object telephonyObject;
//            Object serviceManagerObject;
//            telephonyClass = Class.forName(telephonyName);
//            telephonyStubClass = telephonyClass.getClasses()[0];
//            serviceManagerClass = Class.forName(serviceManagerName);
//            serviceManagerNativeClass = Class.forName(serviceManagerNativeName);
//            Method getService = // getDefaults[29];
//                    serviceManagerClass.getMethod("getService", String.class);
//            Method tempInterfaceMethod = serviceManagerNativeClass.getMethod("asInterface", IBinder.class);
//            Binder tmpBinder = new Binder();
//            tmpBinder.attachInterface(null, "fake");
//            serviceManagerObject = tempInterfaceMethod.invoke(null, tmpBinder);
//            IBinder retbinder = (IBinder) getService.invoke(serviceManagerObject, "phone");
//            Method serviceMethod = telephonyStubClass.getMethod("asInterface", IBinder.class);
//            telephonyObject = serviceMethod.invoke(null, retbinder);
//            telephonyEndCall = telephonyClass.getMethod("endCall");
//            telephonyEndCall.invoke(telephonyObject);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//
//    // Method to disconnect phone automatically and programmatically
//    // Keep this method as it is
//    @SuppressWarnings({ "rawtypes", "unchecked" })
//    private void disconnectPhoneItelephony(Context context)
//    {
//        ITelephony telephonyService;
//        TelephonyManager telephony = (TelephonyManager)
//                context.getSystemService(Context.TELEPHONY_SERVICE);
//        try
//        {
//            Class c = Class.forName(telephony.getClass().getName());
//            Method m = c.getDeclaredMethod("getITelephony");
//            m.setAccessible(true);
//            telephonyService = (ITelephony) m.invoke(telephony);
//            telephonyService.endCall();
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }


}

