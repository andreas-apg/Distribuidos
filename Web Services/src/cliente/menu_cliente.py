import sys

from construtor_de_ordem import ConstrutorDeOrdem
from construtor_de_interesse import ConstrutorDeInteresse
from construtor_de_limite import ConstrutorDeLimite
from loggable import Loggable
from models.ordem import Ordem
from models.interesse import Interesse
from cliente_http import ClienteHttp
import os


class MenuCliente(Loggable):
    """Class responsible for getting the user inputs"""

    def __init__(self, username, cliente_http):
        super().__init__()
        self.nome_de_usuario = username
        self.cliente_http: ClienteHttp = cliente_http

    def start_menu(self):
        """ Prints the menu to the user and get keyboard input """

        self.menu: dict = {}
        self.menu['1'] = "1) Emitir ordem de compra ou venda;" 
        self.menu['2'] = "2) Visualizar minha lista de interesse de cotacao;"
        self.menu['3'] = "3) Atualizar minha lista de interesse de cotacao;"
        self.menu['4'] = "4) Visualizar minha carteira de acoes;"
        self.menu['5'] = "5) Visualizar minha lista de limite de ganho/perca;"
        self.menu['6'] = "6) Atualizar minha lista de limite de ganho/perca;"
        self.menu['7'] = "0) Sair;"

        print("Welcomed {}".format(self.nome_de_usuario))

        while True: 
            print()
            options=sorted(self.menu.keys())
            for entry in options: 
                print (self.menu[entry])

            selection=input("Digite o numero da opcao desejada: ") 
            
            if selection =='1':                 
                print (self.menu[selection])
                try:
                    construtor_de_ordem = ConstrutorDeOrdem(self.nome_de_usuario)
                    ordem: Ordem = construtor_de_ordem.obter_ordem_do_usuario()
                    self.cliente_http.emitir_ordem(ordem)
                except:
                    self.log_error("Nao foi possivel emitir a ordem", exc=sys.exc_info()[0])
                
            elif selection == '2': 
                print (self.menu[selection])
                try:
                    self.cliente_http.obter_interesses()
                except:
                    self.log_error("Nao foi possivel obter cotacoes da lista de interesse", exc=sys.exc_info()[0])
            
            elif selection == '3': 
                print (self.menu[selection])
                try:
                    construtor_de_interesse = ConstrutorDeInteresse(self.nome_de_usuario)
                    interesse = construtor_de_interesse.obter_interesse_do_usuario()
                    self.cliente_http.enviar_interesse(interesse)
                except:
                    self.log_error("Nao foi possivel enviar novo interesse", exc=sys.exc_info()[0])

            elif selection == '4': 
                print (self.menu[selection])
                try:
                    self.cliente_http.obter_carteira()
                except:
                    self.log_error("Nao foi possivel obter carteira", exc=sys.exc_info()[0])
                    
            elif selection == '5': 
                print (self.menu[selection])
                try:
                    self.cliente_http.obter_limites()
                except:
                    self.log_error("Nao foi possivel obter limites", exc=sys.exc_info()[0])

            elif selection == '6': 
                print (self.menu[selection])
                try:
                    construtor_de_limite = ConstrutorDeLimite(self.nome_de_usuario)
                    limite = construtor_de_limite.obter_limite_do_usuario()
                    self.cliente_http.enviar_limite(limite)
                except:
                    self.log_error("Nao foi possivel enviar novo limite", exc=sys.exc_info()[0])                                            

            elif selection == '0': 
                os._exit(1)
                break
            else: 
                print ("Opcao invalida")
