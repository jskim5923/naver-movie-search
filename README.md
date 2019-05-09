## movie search api ToyApplication

네이버 영화검색 open Api를 활용한 어플리케이션

mvvm 패턴, aac(ViewModel, LiveData, DataBinding) 사용


## 사용된 라이브러리
1. [Retrofit](https://github.com/square/retrofit)
2. [Glide](https://github.com/bumptech/glide)
3. [Koin](https://github.com/InsertKoinIO/koin)
4. [RxJava](https://github.com/ReactiveX/RxJava)
5. [Android Architecture Component](https://developer.android.com/topic/libraries/architecture/)


[네이버 API 테스트 방법]

https://developers.naver.com/docs/search/movie/ 에서 API 신청

CLIENT_ID, CLIENT_SECRET정보를 /apikey.properties 파일에 넣고 빌드,실행
			
      예) /apikey.properties      
      NAVER_API_CLIENT_ID = NAVER_API_CLIENT_ID
      NAVER_API_CLIENT_SECRET = NAVER_API_CLIENT_SECRET
      
