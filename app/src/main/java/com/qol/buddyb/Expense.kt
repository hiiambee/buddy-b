package com.qol.buddyb

import java.util.Date

data class Expense(
    var title: String?= null,
    var amount: Int?= null,
    var due: Date?= null,
    var documentId: String? = null
){
    // Add a no-argument constructor
    constructor() : this(null, null, null, null) // Set default values for your fields or make them nullable.
}
