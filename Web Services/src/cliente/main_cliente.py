from menu_cliente import MenuCliente
from cliente_http import ClienteHttp
import time

class MainCliente:
    """Initial class for project with the main function"""

    
    def main(self):

            self.username: str = "john snow"
            server_url: str

            
            self.cliente: ClienteHttp = ClienteHttp(self.username)
            self.cliente.start()
            time.sleep(2)
        
            self.menu: MenuCliente = MenuCliente(self.username, self.cliente)
            self.menu.start_menu()
        
            print ("Hello, world!")

if __name__ == '__main__':
    MainCliente().main()