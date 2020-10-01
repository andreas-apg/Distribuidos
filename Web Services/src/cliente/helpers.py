import logging, sys
from datetime import datetime, time
from loggable import Loggable
from models.enums import *


class Helpers(Loggable):    

    def obter_prazo(self) -> str:
        """ Obtem um prazo valido do usuario no formato de data "dd-MM-yyyy_HH:mm:ss" """
        while True: 
            user_input=input("Digite o prazo de validade da ordem como 'hh:mm:ss' (Default: 00:05:00): ") 
            
            if user_input =='':
                return '00:05:00'
            else:
                try:
                    time.fromisoformat(user_input)
                except ValueError as err:
                    print("Prazo invalido, tente novamente")
                    continue
                except:
                    self.log_error(msg= "Erro obter prazo do usuario")
                    raise
                return user_input


    def obter_quantidade(self) -> int:
        """ Obtem um numero inteiro do usuario """
        while True:
            user_input = input("Digite a quantidade (Default: 100): ")

            if user_input == '':
                return 100
            else:
                try:
                    int_input = int(user_input)
                except TypeError as err:
                    print("Quantidade invalida, tente novamente")
                    continue
                except:
                    self.log_error(msg= "Erro ao obter quantidade do usuario")
                    raise
                return int_input


    def obter_valor(self) -> float:
        """ Obtem um numero decimal do usuario """
        while True:
            user_input = input("Digite o valor (Ex: 22.00): ")

            if user_input == '':
                return 22.00
            else:
                try:
                    dec_input = float(user_input)
                except ValueError as err:
                    print("Valor invalido, tente novamente")
                    continue
                except:
                    self.log_error(msg= "Erro ao obter valor do usuario")
                    raise
                return dec_input
            
    def obter_codigo_da_acao(self) -> str:
        """ Obtem uma string representando o codigo de uma acao """
        
        while True:
            user_input = input("Digite o o codigo da acao (Ex: azul4): ")

            if user_input == '':
                return "azul4"
            else:
                try:
                    if len(user_input) == 5:
                        return user_input.upper()
                    else:
                        raise ValueError
                except ValueError as err:
                    print("Codigo invalido, tente novamente")
                    continue
                except:
                    self.log_error(msg="Erro ao obter valor do usuario")
                    raise
                return user_input
            

    def obter_tipo_da_ordem(self) -> str:
        """ Obtem uma string representando o codigo de uma acao """
        
        while True:
            user_input = input("Digite o tipo da ordem (Ex:'compra' ou 'venda'): ")
            user_input = user_input.lower()

            if(user_input) == 'c':
                return (TipoDaOrdem.COMPRA.value)
            elif(user_input) == 'v':
                return (TipoDaOrdem.VENDA.value)
            else:
                try:
                    tipo = TipoDaOrdem(user_input)
                except ValueError as err:
                    print("Tipo de ordem invalido, tente novamente")
                    continue
                except:
                    self.log_error(msg="Erro ao obter tipo da ordem")
                    raise
                return tipo
    
    def obter_tipo_da_atualizacao(self) -> str:
        """ Obtem uma string representando o codigo de uma acao """
        
        while True:
            user_input = input("Digite o tipo da atualizacao (Ex:'inserir' ou 'remover'): ")
            user_input = user_input.lower()

            if(user_input) == 'c':
                return (TipoDaAtualizacao.INSERIR.value)
            elif(user_input) == 'v':
                return (TipoDaAtualizacao.REMOVER.value)
            else:
                try:
                    tipo = TipoDaAtualizacao(user_input)
                except ValueError as err:
                    print("Tipo de atualizacao invalido, tente novamente")
                    continue
                except:
                    self.log_error(msg="Erro ao obter tipo da atualizacao")
                    raise
                return tipo

    
    def obter_tipo_do_limite(self) -> str:
        """ Obtem uma string representando o codigo de uma acao """
        
        while True:
            user_input = input("Digite o tipo do limite (Ex:'ganho' ou 'perda'): ")
            user_input = user_input.lower()

            if(user_input) == 'g':
                return (TipoDoLimite.GANHO.value)
            elif(user_input) == 'p':
                return (TipoDoLimite.PERDA.value)
            else:
                try:
                    tipo = TipoDoLimite(user_input)
                except ValueError as err:
                    print("Tipo do limite invalido, tente novamente")
                    continue
                except:
                    self.log_error(msg="Erro ao obter tipo do limite")
                    raise
                return tipo


# metodo para testar as funcoes      
if __name__ == '__main__':
    helpers = Helpers()
    # prazo = helpers.obter_prazo()
    # print(prazo)

    # qtd = helpers.obter_quantidade()
    # print(qtd)

    # val = helpers.obter_valor()
    # print(val)

    # val = helpers.obter_tipo_da_ordem()
    # print(val)

    # val = helpers.obter_tipo_da_atualizacao()
    # print(val)

    # val = helpers.obter_tipo_do_limite()
    # print(val)