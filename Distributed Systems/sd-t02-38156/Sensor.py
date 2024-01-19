import requests

api_root_url='http://localhost:8080/sd/'

# APENAS PARA TESTES 
def testar():
    op = input("s -> Mostrar lista antes de inserir?")

    if op == "s":
        # Pesquisa todos os eventos
        response = requests.get(api_root_url+'allP')
        x = response.json()

        # Devolve o nome e data de cada um 
        for e in x:
            print("Nome " + e.get('name'))
            print("ChipID: " + str( e.get('chipId') ) + "\n")

         # Pesquisa todos os eventos
        response = requests.get(api_root_url+'allC')
        x = response.json()

        # Devolve o nome e data de cada um 
        for e in x:
            print("Novo chip: ")
            print("Chip: " + str( e.get('chipId2') ))
            print("START: " + str( e.get('start') ))
            print("P1: " + str( e.get('p1') ))
            print("P2: " + str( e.get('p2') ))
            print("P3: " + str( e.get('p3') ))
            print("FINISH: " + str( e.get('finish') ))
            

testar()
id = int(input("ChipId ->"))
t = input("Timestamp ->")
l = input("Local ->")

response = requests.post(api_root_url+'Chip', 
                            params = {'local': l, 'time': t, 'chipId2':id} )

# É devolvido apenas uma String e não um json
print(response.text)