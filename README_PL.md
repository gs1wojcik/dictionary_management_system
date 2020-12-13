# dictionary_microservices

Schemat bazy MySql:

	\CONFIG-EXAMPLE\mysql_schema.sql
	\CONFIG-EXAMPLE\mysql_schema_with_example_data.sql

Docker images: 
	
	Rest Web Service: https://hub.docker.com/r/gsw91/d-ws2
	Web Application: https://hub.docker.com/r/gsw91/d-ws2_app
	
Docker konfiguracja obrazów:

	*** przykład konfiguracji jest w katalogu "/CONFIG-EXAMPLE/" ***

	Rest Service: 
	
		1. Utwórz /dws-config/
			a. Stwórz plik dws-config.properties
			b. Wstaw zawartość:
				## MySQL DB Access
				dws.app.db_connection_string=YOUR_CONNECTION_STRING
				dws.app.db_user=_YOUR_USER
				dws.app.db_password=YOUR_PASSWORD
				
				np:
				
				## MySQL DB Access
				dws.app.db_connection_string=jdbc:mysql://000.000.0.0:3306/dictionary?serverTimezone=Europe/Warsaw&useSSL=False
				dws.app.db_user=user
				dws.app.db_password=password
				
		2. Utwórz katalog /file-processing/archive/
			a. W katalogu /file-processing/ będą umieszczane pliki do przetworzenia (aktywacja poprzez api, parametr adres usługi webhook)
			b. Do katalogu /file-processing/archive/ będą przenoszone przetworzone pliki
			c. Typ plików do przetwarzania .csv, zawartość to trzy kolumny, pierwszy wiersz nagłówek (jest pomijany), np:
				type;id;setActive
				item;18;0
				dict;10;0
				item;19;0
				dict;3;1			
			
		3. Komendy do stworzenia i włączenia kontenera docker (cmd):
		
			### delete container if exists (optional)
			docker container rm DictionaryWebService
			
			### delete volume if exists (read-only, config file) 
			docker volume rm dws-config
			
			### stwórz kontener z woluminami
			### -v dws-config:/dws-config - pod plik konfiguracyjny
			### --mount type=bind,source=YOUR_OWN_PATH - w miejscu YOUR_OWN_PATH własna ścieżka (HOST) gdzie będą umieszczane pliki to aktywacji/deaktywcji słownikówi itemów)
			docker container create --name DictionaryWebService -v dws-config:/dws-config ^
					--mount type=bind,source=YOUR_OWN_PATH/file-processing,target=/file-processing ^
					-p 8080:8080 gsw91/d-ws2:latest
				
			### skopiuj plik konfiguracyjny dws-config/dws-config.properties do kontenera 
			docker cp dws-config/dws-config.properties DictionaryWebService:/dws-config/dws-config.properties
			docker start DictionaryWebService 
			pause 
			
		4. Po włączeniu kontenera, usługa powinna znajdować się na: localhost:8080/DictionaryWebService (chyba że został użyty inny port)
			
		5. Dokumentacja API (swagger): localhost:8080/DictionaryWebService/swagger-ui/#/

	Web Application: 
		
		1. Komendy do stworzenia i włączenia kontenera docker (cmd):
			### usuń kontener jeżeli istnieje 
			docker container rm DictionaryWebApp
			
			### jako parametr -e "app.base_url=http://000.000.0.0:8080" adres usługi REST Service
			docker run -e "app.base_url=http://000.000.0.0:8080" ^
					-d ^
					-p 8989:8989 ^
					--name DictionaryWebApp ^
					gsw91/d-ws2_app:latest
			
		2. Po włączeniu kontenera, usługa powinna znajdować się na: localhost:8989/dms