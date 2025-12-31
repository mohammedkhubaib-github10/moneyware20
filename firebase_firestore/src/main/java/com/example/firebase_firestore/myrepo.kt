package com.example.firebase_firestore

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

fun testing(name: String) {
    Firebase.firestore.document("user")
}