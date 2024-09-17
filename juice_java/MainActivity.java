package com.cjxpj.juice;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int MANAGE_EXTERNAL_STORAGE_PERMISSION_REQUEST = 100;

    static {
        System.loadLibrary("Juice");
    }

    private native void RunJuice();


    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 从SharedPreferences中读取 iPhoneConfig 开关状态
        SharedPreferences preferences = getSharedPreferences("iPhoneConfig", MODE_PRIVATE);
        boolean switchState = preferences.getBoolean("SwitchState", false); // 默认为false

        // 获取 iPhoneConfig Switch 控件并设置其状态
        Switch switchIPhoneConfig = findViewById(R.id.iPhoneConfig);
        switchIPhoneConfig.setChecked(switchState);
        if (switchState) {
            // 定义日期时间格式
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // 获取当前时间
            Date date = new Date();

            // 获取设备信息
            StringBuilder deviceInfo = new StringBuilder();
            // 返回格式化后的时间字符串
            deviceInfo
                    .append("更新时间: ").append(dateFormat.format(date)).append("\n")
                    .append("品牌=").append(Build.BRAND).append("\n")
                    .append("设备名=").append(Build.DEVICE).append("\n")
                    .append("型号=").append(Build.MODEL).append("\n")
                    .append("Android版本=").append(Build.VERSION.RELEASE).append("\n")
                    .append("SDK版本=").append(Build.VERSION.SDK_INT).append("\n");

            // 获取存储信息
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            long total = statFs.getBlockCountLong() * statFs.getBlockSizeLong();
            long available = statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong();

            deviceInfo.append("存储总量=").append(formatSize(total)).append("\n");
            deviceInfo.append("可用存储=").append(formatSize(available));

            writeDataToFile("juiceData/system/database", "appConfig.txt", deviceInfo.toString());
        }

        // 从SharedPreferences中读取 NoSleep 开关状态
        preferences = getSharedPreferences("NoSleep", MODE_PRIVATE);
        switchState = preferences.getBoolean("SwitchState", false);

        // 获取 NoSleep Switch 控件并设置其状态
        Switch switchNoSleep = findViewById(R.id.NoSleep);
        switchNoSleep.setChecked(switchState);

        if (switchState) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                showWakeLockNotification();
            }
        }

        // 启动程序并检查权限
        checkAndRequestPermissions();
    }

    private String formatSize(long size) {
        String suffix = null;
        float fSize = size;

        if (fSize >= 1024) {
            suffix = "KB";
            fSize /= 1024;
            if (fSize >= 1024) {
                suffix = "MB";
                fSize /= 1024;
            }
            if (fSize >= 1024) {
                suffix = "GB";
                fSize /= 1024;
            }
        }

        StringBuilder resultBuffer = new StringBuilder(Float.toString(fSize));
        int commaOffset = resultBuffer.indexOf(".");
        if (commaOffset >= 0) {
            int endIndex = Math.min(commaOffset + 3, resultBuffer.length());
            resultBuffer.setLength(endIndex);
        }
        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showWakeLockNotification() {
        String channelId = "NoSleepChannel";
        String channelName = "NoSleep 禁止休眠";
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // 创建通知渠道 (Android 8.0 及以上需要)
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW);
        notificationManager.createNotificationChannel(channel);

        // 创建前往电池优化设置的Intent
        Intent intent = new Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // 构建通知
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_notification_icon)  // 设置通知的小图标
                .setContentTitle("运行中")
                .setContentText("点击以设置无限制省电策略")
                .setPriority(NotificationCompat.PRIORITY_LOW)  // 设置通知优先级
                .setOngoing(true)  // 设置为持久通知
                .setAutoCancel(false)  // 防止用户点击通知后自动取消
                .setContentIntent(pendingIntent);  // 添加点击行为

        // 显示通知
        notificationManager.notify(1, builder.build());
    }



    public void OpenPath(View view) {
        Toast.makeText(this, "正在尝试通过其他客户端打开文档", Toast.LENGTH_SHORT).show();

        // 指向外部存储Documents目录下的juiceData文件夹中的README.md文件
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/juiceData", "README.md");

        // 验证文件是否存在
        if (file.exists() && file.isFile()) {
            try {
                Uri uri = Uri.parse(file.toString());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "text/markdown");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                // 使用选择器让用户选择打开文件夹的应用
                startActivity(Intent.createChooser(intent, "选择一个客户端打开文档"));
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "未找到可以打开此文件夹的应用", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "打开文件夹时出错: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "文件夹不存在", Toast.LENGTH_SHORT).show();
        }
    }

    public void OpenGithub(View view) {
        Toast.makeText(this, "打开Github中", Toast.LENGTH_SHORT).show();
        try {
            // 要打开的URL链接
            String url = "https://github.com/cjxpj";

            // 创建一个Intent来启动浏览器
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "无法打开链接", Toast.LENGTH_SHORT).show();
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    public void OpenGitee(View view) {
        Toast.makeText(this, "打开Gitee中", Toast.LENGTH_SHORT).show();
        try {
            // 要打开的URL链接
            String url = "https://gitee.com/cjxpj";

            // 创建一个Intent来启动浏览器
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "无法打开链接", Toast.LENGTH_SHORT).show();
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }


    @SuppressLint("UseSwitchCompatOrMaterialCode")
    public void NoSleep(View view) {
        Switch switchConfig = (Switch) view;
        boolean isChecked = switchConfig.isChecked();

        // 保存开关状态到SharedPreferences
        SharedPreferences preferences = getSharedPreferences("NoSleep", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("SwitchState", isChecked);
        editor.apply();

        // 根据开关状态显示Toast消息
        if (isChecked) {
            Toast.makeText(this, "重启后生效", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "关闭成功", Toast.LENGTH_SHORT).show();
        }
    }
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    public void iPhoneConfig(View view) {
        Switch switchConfig = (Switch) view;
        boolean isChecked = switchConfig.isChecked();

        // 保存开关状态到SharedPreferences
        SharedPreferences preferences = getSharedPreferences("iPhoneConfig", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("SwitchState", isChecked);
        editor.apply();

        // 根据开关状态显示Toast消息
        if (isChecked) {
            Toast.makeText(this, "重启后生效", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "关闭成功", Toast.LENGTH_SHORT).show();
        }
    }


    private void checkAndRequestPermissions() {
        String[] permissions;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions = new String[]{
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO,
                    Manifest.permission.READ_MEDIA_AUDIO
            };
        } else {
            permissions = new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
        }

        if (!hasAllPermissionsGranted(permissions)) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
        } else {
            checkManageExternalStoragePermission();
        }
    }

    private boolean hasAllPermissionsGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /** @noinspection deprecation*/
    private void checkManageExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, MANAGE_EXTERNAL_STORAGE_PERMISSION_REQUEST);
            } else {
                accessExternalStorage();
            }
        } else {
            accessExternalStorage();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MANAGE_EXTERNAL_STORAGE_PERMISSION_REQUEST && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                accessExternalStorage();
            } else {
                Toast.makeText(this, "请授予所有文件访问权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void accessExternalStorage() {
        callNativeMethod();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (allPermissionsGranted(grantResults)) {
                Toast.makeText(this, "获取权限成功", Toast.LENGTH_SHORT).show();
                checkManageExternalStoragePermission();
            } else {
                Toast.makeText(this, "获取权限失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean allPermissionsGranted(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void callNativeMethod() {
        try {
            RunJuice();
            Toast.makeText(this, "启动成功", Toast.LENGTH_SHORT).show();
        } catch (UnsatisfiedLinkError e) {
            Toast.makeText(this, "启动失败", Toast.LENGTH_SHORT).show();
            writeDataToFile("juiceData/system/private","error.txt",e.toString());
        } catch (Exception e) {
            Toast.makeText(this, "未知错误", Toast.LENGTH_SHORT).show();
            writeDataToFile("juiceData/system/private","error.txt",e.toString());
        }
    }

    private void writeDataToFile(String filepath,String filename,String str) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), filepath);
            if (!dir.exists() && !dir.mkdirs()) {
                Toast.makeText(this, "创建目录失败", Toast.LENGTH_SHORT).show();
                return;
            }
            File file = new File(dir, filename);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(str.getBytes(StandardCharsets.UTF_8));
                fos.flush();
//                Toast.makeText(this, "写入文件位置: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(this, "写入文件失败", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "写入文件失败", Toast.LENGTH_SHORT).show();
        }
    }
}
