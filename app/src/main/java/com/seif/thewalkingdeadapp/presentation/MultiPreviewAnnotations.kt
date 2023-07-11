package com.seif.thewalkingdeadapp.presentation

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview(uiMode = UI_MODE_NIGHT_NO)
annotation class LightDarkModePreviews

@Preview (name = "PIXEL_XL", device = Devices.PIXEL_XL, showSystemUi = true)
@Preview (name = "PIXEL_2", device = Devices.PIXEL_2, showSystemUi = true)
@Preview (name = "PIXEL_3", device = Devices.PIXEL_3, showSystemUi = true)
@Preview (name = "PIXEL_4", device = Devices.PIXEL_4, showSystemUi = true)
annotation class DevicesPreviews