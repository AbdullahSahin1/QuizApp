package com.example.quizapp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class QuizQuestion(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)

data class QuizUiState(
    val questions: List<QuizQuestion> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val selectedAnswerIndex: Int? = null,
    val score: Int = 0,
    val isQuizCompleted: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)

class QuizViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    init {
        loadQuestions()
    }

    private fun loadQuestions() {
        _uiState.update { it.copy(isLoading = true) }
        
        // Örnek sorular
        val sampleQuestions = listOf(
            QuizQuestion(
                id = 1,
                question = "Türkiye'nin başkenti neresidir?",
                options = listOf("İstanbul", "Ankara", "İzmir", "Bursa"),
                correctAnswerIndex = 1
            ),
            QuizQuestion(
                id = 2,
                question = "Hangi gezegen Güneş Sisteminin en büyük gezegenidir?",
                options = listOf("Mars", "Venüs", "Jüpiter", "Satürn"),
                correctAnswerIndex = 2
            ),
            QuizQuestion(
                id = 3,
                question = "İnsan vücudundaki en büyük organ hangisidir?",
                options = listOf("Kalp", "Beyin", "Deri", "Karaciğer"),
                correctAnswerIndex = 2
            ),
            QuizQuestion(
                id = 4,
                question = "Hangi element periyodik tabloda 'Fe' sembolü ile gösterilir?",
                options = listOf("Demir", "Flor", "Fosfor", "Fermiyum"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 5,
                question = "Hangi yıl Türkiye Cumhuriyeti kurulmuştur?",
                options = listOf("1920", "1921", "1922", "1923"),
                correctAnswerIndex = 3
            ),
            QuizQuestion(
                id = 6,
                question = "Fransa'nın başkenti neresidir?",
                options = listOf("Berlin", "Paris", "Madrid", "Brüksel"),
                correctAnswerIndex = 1
            ),
            QuizQuestion(
                id = 7,
                question = "En uzun kara sınırına sahip ülke hangisidir?",
                options = listOf("ABD", "Çin", "Rusya", "Kanada"),
                correctAnswerIndex = 2
            ),
            QuizQuestion(
                id = 8,
                question = "Dünyanın en derin gölü hangisidir?",
                options = listOf("Baykal Gölü", "Tanganika Gölü", "Hazar Denizi", "Viktorya Gölü"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 9,
                question = "Leonardo da Vinci'nin ünlü tablosu Mona Lisa hangi müzede sergilenmektedir?",
                options = listOf("Louvre Müzesi", "British Museum", "Vatican Müzesi", "Prado Müzesi"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 10,
                question = "Hangi şehir iki kıta üzerinde kurulmuştur?",
                options = listOf("İstanbul", "Moskova", "Tokyo", "New York"),
                correctAnswerIndex = 0
            ),QuizQuestion(
                id = 11,
                question = "Türkiye'nin en yüksek dağı hangisidir?",
                options = listOf("Erciyes", "Kaçkar", "Ağrı", "Nemrut"),
                correctAnswerIndex = 2
            ),
            QuizQuestion(
                id = 12,
                question = "Hangi deniz en tuzlu sudur?",
                options = listOf("Karadeniz", "Akdeniz", "Kızıldeniz", "Ölü Deniz"),
                correctAnswerIndex = 3
            ),
            QuizQuestion(
                id = 13,
                question = "İlk Türk alfabesi hangisidir?",
                options = listOf("Göktürk", "Uygur", "Latin", "Arap"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 14,
                question = "En uzun nehir hangisidir?",
                options = listOf("Amazon", "Nil", "Mississippi", "Yangtze"),
                correctAnswerIndex = 1
            ),
            QuizQuestion(
                id = 15,
                question = "Hangi gezegenin halkaları vardır?",
                options = listOf("Dünya", "Mars", "Satürn", "Merkür"),
                correctAnswerIndex = 2
            ),
            QuizQuestion(
                id = 16,
                question = "Dünya Sağlık Örgütü'nün kısaltması nedir?",
                options = listOf("NATO", "UNICEF", "WHO", "UNESCO"),
                correctAnswerIndex = 2
            ),
            QuizQuestion(
                id = 17,
                question = "Einstein'ın ünlü teorisi nedir?",
                options = listOf("Kuantum Mekaniği", "Görelilik", "Big Bang", "Termodinamik"),
                correctAnswerIndex = 1
            ),
            QuizQuestion(
                id = 18,
                question = "Osmanlı Devleti kaç yılında yıkılmıştır?",
                options = listOf("1918", "1920", "1922", "1923"),
                correctAnswerIndex = 2
            ),
            QuizQuestion(
                id = 19,
                question = "İngiltere'nin para birimi nedir?",
                options = listOf("Euro", "Dolar", "Sterlin", "Frank"),
                correctAnswerIndex = 2
            ),
            QuizQuestion(
                id = 20,
                question = "DNA'nın açılımı nedir?",
                options = listOf("Deoksiribonükleik Asit", "Nükleer Asit", "Ribonükleik Asit", "Dinamik Nöron Asidi"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 21,
                question = "Ay'a ilk ayak basan insan kimdir?",
                options = listOf("Neil Armstrong", "Buzz Aldrin", "Yuri Gagarin", "Alan Shepard"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 22,
                question = "Türkiye'nin yüzölçümü en büyük ili hangisidir?",
                options = listOf("Konya", "Ankara", "Sivas", "Antalya"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 23,
                question = "En çok konuşulan dil hangisidir?",
                options = listOf("İngilizce", "İspanyolca", "Çince", "Hintçe"),
                correctAnswerIndex = 2
            ),
            QuizQuestion(
                id = 24,
                question = "Leonardo da Vinci'nin ünlü tablosu hangisidir?",
                options = listOf("Yıldızlı Gece", "Mona Lisa", "Çığlık", "Son Akşam Yemeği"),
                correctAnswerIndex = 1
            ),
            QuizQuestion(
                id = 25,
                question = "Anayasamız hangi yılda kabul edilmiştir?",
                options = listOf("1980", "1982", "2000", "1995"),
                correctAnswerIndex = 1
            ),
            QuizQuestion(
                id = 26,
                question = "Nobel ödüllerini kim verir?",
                options = listOf("BM", "İsveç Akademisi", "NATO", "AB"),
                correctAnswerIndex = 1
            ),
            QuizQuestion(
                id = 27,
                question = "Türk lirasının kuruş cinsi nedir?",
                options = listOf("Kuruş", "Cent", "Penny", "Dinar"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 28,
                question = "Dünyanın en derin gölü hangisidir?",
                options = listOf("Hazar", "Baykal", "Tanganika", "Victoria"),
                correctAnswerIndex = 1
            ),
            QuizQuestion(
                id = 29,
                question = "Mevlana hangi şehirde yaşamıştır?",
                options = listOf("Konya", "İstanbul", "Bursa", "Şanlıurfa"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 30,
                question = "Türkiye'de ilk nüfus sayımı ne zaman yapılmıştır?",
                options = listOf("1927", "1935", "1945", "1950"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 31,
                question = "Hangi gezegen 'Kızıl Gezegen' olarak bilinir?",
                options = listOf("Venüs", "Mars", "Jüpiter", "Uranüs"),
                correctAnswerIndex = 1
            ),
            QuizQuestion(
                id = 32,
                question = "Köroğlu kimdir?",
                options = listOf("Yazar", "Şair", "Halk kahramanı", "Bilim insanı"),
                correctAnswerIndex = 2
            ),
            QuizQuestion(
                id = 33,
                question = "Türkiye'nin en büyük gölü hangisidir?",
                options = listOf("Van Gölü", "Tuz Gölü", "Beyşehir Gölü", "Eğirdir Gölü"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 34,
                question = "İslamiyet öncesi Türk destanlarından biri nedir?",
                options = listOf("Bozkurt", "İlyada", "Manas", "Oğuz Kağan"),
                correctAnswerIndex = 3
            ),
            QuizQuestion(
                id = 35,
                question = "Orman varlığı bakımından zengin ilimiz hangisidir?",
                options = listOf("Artvin", "Mersin", "Antalya", "İzmir"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 36,
                question = "Hangi şehir 'Şehirlerin Sultanı' olarak anılır?",
                options = listOf("İstanbul", "Roma", "Mekke", "Kudüs"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 37,
                question = "İngilizce'de 'elma' ne demektir?",
                options = listOf("Apple", "Orange", "Banana", "Pear"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 38,
                question = "UNESCO'nun açılımı nedir?",
                options = listOf("Eğitim, Bilim ve Kültür Örgütü", "Sağlık Örgütü", "Çocuk Fonu", "Kalkınma Ajansı"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 39,
                question = "Türkiye'deki en büyük ada hangisidir?",
                options = listOf("Gökçeada", "Bozcaada", "Marmara Adası", "Kınalıada"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 40,
                question = "Güneş'e en yakın gezegen hangisidir?",
                options = listOf("Venüs", "Merkür", "Mars", "Dünya"),
                correctAnswerIndex = 1
            ),
            QuizQuestion(
                id = 41,
                question = "Kudüs hangi üç din için kutsaldır?",
                options = listOf("İslam, Hristiyanlık, Musevilik", "Budizm, İslam, Hinduizm", "Yahudilik, Hristiyanlık, Zerdüştlük", "İslam, Hinduizm, Musevilik"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 42,
                question = "Suyun donma noktası kaç derecedir?",
                options = listOf("0°C", "100°C", "32°C", "-1°C"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 43,
                question = "Türkiye'nin ilk kadın pilotu kimdir?",
                options = listOf("Sabiha Gökçen", "Afet İnan", "Halide Edip", "Feriha Sanerk"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 44,
                question = "İstanbul'un fethi hangi padişah zamanında gerçekleşti?",
                options = listOf("Yavuz Sultan Selim", "II. Mehmet", "I. Murat", "Orhan Gazi"),
                correctAnswerIndex = 1
            ),
            QuizQuestion(
                id = 45,
                question = "Tarihte yazıyı ilk hangi uygarlık bulmuştur?",
                options = listOf("Sümerler", "Hititler", "Mısırlılar", "İyonlar"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 46,
                question = "Bir yılda kaç gün vardır (artık yıl hariç)?",
                options = listOf("365", "364", "366", "360"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 47,
                question = "Türkiye'nin en uzun nehri hangisidir?",
                options = listOf("Fırat", "Kızılırmak", "Dicle", "Yeşilırmak"),
                correctAnswerIndex = 1
            ),
            QuizQuestion(
                id = 48,
                question = "Hangi hayvan 'çöl gemisi' olarak bilinir?",
                options = listOf("At", "Deve", "Fil", "Kanguru"),
                correctAnswerIndex = 1
            ),
            QuizQuestion(
                id = 49,
                question = "İlk çağ filozoflarından biri kimdir?",
                options = listOf("Sokrates", "Newton", "Einstein", "Darwin"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 50,
                question = "Hangi şehir iki kıtada yer alır?",
                options = listOf("İstanbul", "Ankara", "İzmir", "Bursa"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 51,
                question = "Türkiye'nin en kalabalık ikinci şehri hangisidir?",
                options = listOf("Ankara", "İzmir", "Bursa", "Antalya"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 52,
                question = "İzmir hangi bölgemizdedir?",
                options = listOf("Ege", "Marmara", "Akdeniz", "Karadeniz"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 53,
                question = "Atatürk'ün doğum yılı nedir?",
                options = listOf("1881", "1885", "1890", "1875"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 54,
                question = "Suyun kimyasal formülü nedir?",
                options = listOf("H2O", "CO2", "NaCl", "O2"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 55,
                question = "Aşağıdakilerden hangisi bir elementtir?",
                options = listOf("Su", "Hidrojen", "Tuz", "Şeker"),
                correctAnswerIndex = 1
            ),
            QuizQuestion(
                id = 56,
                question = "Hangi ülke Avrupa kıtasında değildir?",
                options = listOf("Almanya", "İspanya", "Brezilya", "Fransa"),
                correctAnswerIndex = 2
            ),
            QuizQuestion(
                id = 57,
                question = "İstanbul Boğazı hangi iki denizi birbirine bağlar?",
                options = listOf("Karadeniz - Marmara", "Ege - Akdeniz", "Marmara - Ege", "Karadeniz - Ege"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 58,
                question = "Hz. Muhammed nerede doğmuştur?",
                options = listOf("Mekke", "Medine", "Kudüs", "Taif"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 59,
                question = "İstiklal Marşı'nın yazarı kimdir?",
                options = listOf("Mehmet Akif Ersoy", "Namık Kemal", "Ziya Gökalp", "Tevfik Fikret"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 60,
                question = "Türk bayrağındaki ay ve yıldız neyi temsil eder?",
                options = listOf("İslamiyet", "Güneş", "Bağımsızlık", "Kültür"),
                correctAnswerIndex = 0
            )
        )
        
        _uiState.update { 
            it.copy(
                questions = sampleQuestions,
                isLoading = false
            )
        }
    }

    fun selectAnswer(answerIndex: Int) {
        val currentState = _uiState.value
        val currentQuestion = currentState.questions.getOrNull(currentState.currentQuestionIndex) ?: return
        
        _uiState.update { it.copy(selectedAnswerIndex = answerIndex) }
        
        // Cevap doğru mu kontrol et
        if (answerIndex == currentQuestion.correctAnswerIndex) {
            _uiState.update { it.copy(score = it.score + 1) }
        }
    }

    fun nextQuestion() {
        val currentState = _uiState.value
        
        if (currentState.currentQuestionIndex < currentState.questions.size - 1) {
            _uiState.update { 
                it.copy(
                    currentQuestionIndex = it.currentQuestionIndex + 1,
                    selectedAnswerIndex = null
                )
            }
        } else {
            _uiState.update { it.copy(isQuizCompleted = true) }
        }
    }

    fun restartQuiz() {
        _uiState.update { 
            QuizUiState(
                questions = it.questions,
                currentQuestionIndex = 0,
                selectedAnswerIndex = null,
                score = 0,
                isQuizCompleted = false
            )
        }
    }
} 