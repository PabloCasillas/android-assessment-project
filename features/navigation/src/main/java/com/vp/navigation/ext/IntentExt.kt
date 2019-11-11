package com.vp.navigation.ext

import android.content.Intent
import android.net.Uri

fun Uri.Builder.toViewIntent() = Intent(Intent.ACTION_VIEW, Uri.parse(this.build().toString()))