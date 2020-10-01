import sys
from construtor_de_ordem import ConstrutorDeOrdem
from models.ordem import Ordem
from loggable import Loggable

class MenuCliente(Loggable):
    """Class responsible for getting the user inputs"""

    def __init__(self, username, cliente):
        super().__init__()
        self.nome_de_usuario = username
        self.cliente: ClienteHttp = cliente

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
                    self.cliente.emitir_ordem(ordem)
                except:
                    self.log_error("Nao foi possivel emitir a ordem", exc=sys.exc_info()[0])
                
            elif selection == '2': 
                print ("delete")
            elif selection == '3':
                print ("find")
            elif selection == '4': 
                break
            else: 
                print ("Opcao invalida")