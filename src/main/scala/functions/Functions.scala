package functions
import java.text.DecimalFormat

import bill.Bill
import product.Product
import quantityProduct.QuantityProduct

trait Functions {
    // Decorador para decimales
    val df = new DecimalFormat("#.##")

    // Constantes
    val Iva : Double = 0.19
    val Ipoconsumo : Double = 0.16

    def createBill() : Bill
    def calculateIVA(total : Double) : Double
    def calculateIpoconsumo(prod : Product) : Double
    def getTotalProduct(prod : Product, quantity: Int) : Double
    def getTotal(bill : Bill) : Double
    def getTotalIVA(bill : Bill) : Double
    def showProducts(bill : Bill) : Unit
    def showTotal(bill : Bill) : Unit
    def showTotalIVA(bill : Bill) : Unit
    def showTotalWithIVA(bill : Bill) : Unit
    def showBillDetails(bill : Bill) : Unit
}

object Functions extends Functions {

    def createBill() : Bill = {
        // Creando productos por defecto
        val prod1 = Product("Bread", 15.5, false)
        val prod2 = Product("Milk", 25.5, false)
        val prod3 = Product("Pepsi", 83.0, true)
        val prod4 = Product("Oreo", 62.5, true)

        val quantityProd1 = QuantityProduct(prod1, 2, getTotalProduct(prod1, 2))
        val quantityProd2 = QuantityProduct(prod2, 1, getTotalProduct(prod2, 1))
        val quantityProd3 = QuantityProduct(prod3, 3, getTotalProduct(prod3, 3))
        val quantityProd4 = QuantityProduct(prod4, 6, getTotalProduct(prod4, 6))
        val productsList = List(quantityProd1, quantityProd2, quantityProd3, quantityProd4)
        // Creando la factura
        Bill(1234, productsList)
    }

    def calculateIVA(total : Double) : Double = {
        total * Iva
    }

    def calculateIpoconsumo(prod : Product) : Double = {
        prod.ipoconsumo match {
            case true => prod.price * (1 + Ipoconsumo)
            case false => prod.price
        }
    }

    def getTotalProduct(prod : Product, quantity: Int) : Double = {
        calculateIpoconsumo(prod) * quantity
    }

    def getTotal(bill : Bill) : Double = {
        bill.products.map(x => x.total).sum
    }

    def getTotalIVA(bill : Bill) : Double = {
        bill.products.map(x => calculateIVA(x.total)).sum
    }

    def showProducts(bill : Bill) : Unit = {
        bill.products.foreach(x => println(s"Name: ${x.product.name} \t| Price: ${df.format(calculateIpoconsumo(x.product))} \t| Quantity: ${x.quantity} \t| Total: ${x.total}"))
    }

    def showTotal(bill : Bill) : Unit = {
        println(s"Total price without IVA: ${df.format(getTotal(bill))}")
    }

    def showTotalIVA(bill : Bill) : Unit = {
        println(s"Total IVA: ${df.format(getTotalIVA(bill))}")
    }

    def showTotalWithIVA(bill : Bill) : Unit = {
        println(s"Total price with IVA: ${df.format(getTotal(bill) + getTotalIVA(bill))}")
    }

    def showBillDetails(bill : Bill) : Unit = {
        println(s"Bill #${bill.id}")
        println("Products:")
        showProducts(bill)
        showTotal(bill)
        showTotalIVA(bill)
        showTotalWithIVA(bill)
    }

}
