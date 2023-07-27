package com.qol.buddyb

import java.util.Date

data class Bills(
    var title: String?= null,
    var amount: Long ?= null,
    var due: Date ?= null
)
