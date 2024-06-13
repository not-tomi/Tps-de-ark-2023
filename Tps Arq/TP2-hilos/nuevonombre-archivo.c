#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

#define NUMBER_OF_THREADS 2
#define CANTIDAD_INICIAL_HAMBURGUESAS 20

int cantidad_restante_hamburguesas = CANTIDAD_INICIAL_HAMBURGUESAS;
int turno = 0; 

void *comer_hamburguesa(void *tid)
{
    int mi_turno = (int)tid;
    while (1 == 1)
    { 
        while (turno != mi_turno);

        if (cantidad_restante_hamburguesas > 0)
        {
            printf("Hola! soy el hilo(comensal) %d , me voy a comer una hamburguesa ! ya que todavía queda/n %d \n", mi_turno, cantidad_restante_hamburguesas);
            cantidad_restante_hamburguesas--; // Me como una hamburguesa
            turno = (turno + 1) % NUMBER_OF_THREADS; // Pasar el turno al siguiente hilo
        }
        else
        {
            printf("SE TERMINARON LAS HAMBURGUESAS :( \n");
            pthread_exit(NULL); // Forzar terminación del hilo
        }
    }
}

int main(int argc, char *argv[])
{
    pthread_t threads[NUMBER_OF_THREADS];
    int status, i, ret;
    for (int i = 0; i < NUMBER_OF_THREADS; i++)
    {
        printf("Hola!, soy el hilo principal. Estoy creando el hilo %d \n", i);
        status = pthread_create(&threads[i], NULL, comer_hamburguesa, (void *)i);
        if (status != 0)
        {
            printf("Algo salió mal, al crear el hilo recibí el código de error %d \n", status);
            exit(-1);
        }
    }

    for (i = 0; i < NUMBER_OF_THREADS; i++)
    {
        void *retval;
        ret = pthread_join(threads[i], &retval); // Espero por la terminación de los hilos que creé
    }
    pthread_exit(NULL); // Como los hilos que creé ya terminaron de ejecutarse, termino yo también.
}
