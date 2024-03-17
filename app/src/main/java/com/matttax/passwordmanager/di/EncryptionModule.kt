package com.matttax.passwordmanager.di

import android.security.keystore.KeyProperties
import com.matttax.passwordmanager.encryption.config.EncryptionConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class EncryptionModule {

    @Provides
    fun provideDefaultEncryptionConfig(): EncryptionConfig {
        return EncryptionConfig(
            algorithm = KeyProperties.KEY_ALGORITHM_AES,
            blockMode = KeyProperties.BLOCK_MODE_CBC,
            padding = KeyProperties.ENCRYPTION_PADDING_PKCS7,
            chunkSize = 1024 * 4,
            keySize = 16
        )
    }
}
