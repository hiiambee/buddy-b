package com.qol.buddyb

import java.util.Date

data class SavingR(
    var title: String?= null,
    var amount: Long ?= null,
    var due: Date?= null,
    var documentId: String? = null
)
