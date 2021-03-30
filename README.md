# Hava_Durumu
Hava Durumu - API - openweathermap

API ile hava durumu bilgisini çeken bu uygulamada istediğiniz şehrin (Dünya Geneli) hava durumu bilgisini elde edebilirsiniz.
https://openweathermap.org sitesinin ücretsiz API hizmetindne faydalanılarak geliştirildi.


200.000'den fazla şehrin anlık hava durumu çekilebilir.
Veriler JSON, XML ve HTML formatında alınıp kullanılabilir. Bu uygulamada JSON formatında alınıp işlendi.
Aşağıda örnek bir çıktı yer almakta.

{
   "coord":{
      "lon":126.9778,
      "lat":37.5683
   },
   "weather":[
      {
         "id":800,
         "main":"Clear",
         "description":"clear sky",
         "icon":"01d"
      }
   ],
   "base":"stations",
   "main":{
      "temp":282.22,
      "feels_like":280.03,
      "temp_min":280.15,
      "temp_max":283.15,
      "pressure":1013,
      "humidity":57
   },
   "visibility":10000,
   "wind":{
      "speed":0.51,
      "deg":260
   },
   "clouds":{
      "all":0
   },
   "dt":1617063588,
   "sys":{
      "type":1,
      "id":8105,
      "country":"KR",
      "sunrise":1617052851,
      "sunset":1617097928
   },
   "timezone":32400,
   "id":1835848,
   "name":"Seoul",
   "cod":200
}

![Screenshot_1617104762](https://user-images.githubusercontent.com/38869245/112984983-41b5ec80-9168-11eb-92e4-96a21aae4426.png)

