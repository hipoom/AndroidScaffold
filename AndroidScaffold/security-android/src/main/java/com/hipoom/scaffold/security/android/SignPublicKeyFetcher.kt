package com.hipoom.scaffold.security.android

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.os.Build
import androidx.annotation.RequiresApi
import java.io.ByteArrayInputStream
import java.lang.Exception
import java.security.PublicKey
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate

/**
 * 获取当前安装包使用的签名证书中的公钥。
 *
 * @author 郑海鹏
 * @since 2021/12/7 12:10 上午
 */
class SignPublicKeyFetcher {

    /* ======================================================= */
    /* Public Methods                                          */
    /* ======================================================= */

    fun fetch(context: Context): PublicKey? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            fetch28(context)
        } else {
            fetchBefore28(context)
        }
    }



    /* ======================================================= */
    /* Private Methods                                         */
    /* ======================================================= */

    @SuppressLint("PackageManagerGetSignatures")
    private fun fetchBefore28(context: Context): PublicKey? {
        try {
            val pm = context.packageManager
            val pkgName = context.packageName
            val info = pm.getPackageInfo(pkgName, PackageManager.GET_SIGNATURES)
            val signature = info.signatures[0]
            return getPublicKey(signature)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun fetch28(context: Context): PublicKey? {
        try {
            val pm = context.packageManager
            val pkgName = context.packageName
            val info = pm.getPackageInfo(pkgName, PackageManager.GET_SIGNING_CERTIFICATES)
            val signature = info.signingInfo.apkContentsSigners[0]
            return getPublicKey(signature)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun getPublicKey(signature: Signature): PublicKey? {
        try {
            val certFactory = CertificateFactory.getInstance("X.509")
            val byteInputStream = ByteArrayInputStream(signature.toByteArray())
            val cert = certFactory.generateCertificate(byteInputStream) as X509Certificate
            return cert.publicKey
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}