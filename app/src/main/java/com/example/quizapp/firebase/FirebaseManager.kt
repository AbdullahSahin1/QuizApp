package com.example.quizapp.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseManager private constructor() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()

    companion object {
        private const val TAG = "FirebaseManager"
        @Volatile
        private var instance: FirebaseManager? = null

        fun getInstance(): FirebaseManager {
            return instance ?: synchronized(this) {
                instance ?: FirebaseManager().also { instance = it }
            }
        }
    }

    val currentUser: FirebaseUser?
        get() = auth.currentUser

    // Kullanıcı kimlik doğrulama işlemleri
    suspend fun signIn(email: String, password: String): Result<FirebaseUser> = try {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        Result.success(result.user!!)
    } catch (e: Exception) {
        Log.e(TAG, "Giriş başarısız", e)
        Result.failure(e)
    }

    suspend fun signUp(email: String, password: String): Result<FirebaseUser> = try {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        Result.success(result.user!!)
    } catch (e: Exception) {
        Log.e(TAG, "Kayıt başarısız", e)
        Result.failure(e)
    }

    fun signOut() {
        auth.signOut()
    }

    // Firestore işlemleri
    suspend fun saveQuizResult(userId: String, score: Int, totalQuestions: Int) = try {
        val result = hashMapOf(
            "userId" to userId,
            "score" to score,
            "totalQuestions" to totalQuestions,
            "timestamp" to System.currentTimeMillis()
        )
        firestore.collection("quiz_results")
            .add(result)
            .await()
        Result.success(Unit)
    } catch (e: Exception) {
        Log.e(TAG, "Quiz sonucu kaydedilemedi", e)
        Result.failure(e)
    }

    suspend fun getUserResults(userId: String): Result<List<Map<String, Any>>> = try {
        val snapshot = firestore.collection("quiz_results")
            .whereEqualTo("userId", userId)
            .get()
            .await()
        
        val results = snapshot.documents.map { it.data ?: emptyMap() }
        Result.success(results)
    } catch (e: Exception) {
        Log.e(TAG, "Kullanıcı sonuçları alınamadı", e)
        Result.failure(e)
    }

    fun isUserLoggedIn() = auth.currentUser != null
} 