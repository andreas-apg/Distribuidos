from menu_cliente import MenuCliente
from cliente_http import ClienteHttp
import time

class MainCliente:
    """Initial class for project with the main function"""

    def main(self):

            print("Servico Broker!")
            
            self.username: str = obter_nome_de_usuario()
            
            self.cliente: ClienteHttp = ClienteHttp(self.username)
            self.cliente.start()
            time.sleep(1)
        
            self.menu: MenuCliente = MenuCliente(self.username, self.cliente)
            self.menu.start_menu()
        
            print ("Hello, world!")

def obter_nome_de_usuario()->str:
    username = input("Digite o nome de usuario (Ex: warren): ")
    if username == '':
        return "warren"
    else:
        return username


        
        

if __name__ == '__main__':
    MainCliente().main()