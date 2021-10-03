import psycopg2
import requests
import shutil
import time

try:
    print('Connecting to the PostgreSQL database...')
    conn = psycopg2.connect(
        host="localhost",
        database="BookRecommendations",
        user="postgres",
        password="admin")

    cursor = conn.cursor()
    
    print('Fetching data...')
    cursor.execute('SELECT * from book LIMIT 100')
    result = cursor.fetchall()
    for book in result:
        coverLink = "http://covers.openlibrary.org/b/isbn/"+book[2]+"-M.jpg"
        response = requests.get(coverLink)
        if response.status_code == 200:
            print("Book:",book[0])
            with open("BookCovers/"+book[2]+".jpg",'wb') as f:
                shutil.copyfileobj(response.raw,f)
        time.sleep(2)

except(Exception, psycopg2.DatabaseError, requests.exceptions.HTTPError) as err:
    print(err)
finally:
    if conn is not None:
        conn.close()
    print('Database connection closed.')