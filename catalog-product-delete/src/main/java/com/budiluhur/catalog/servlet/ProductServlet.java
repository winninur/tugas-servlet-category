package com.budiluhur.catalog.servlet;

import com.budiluhur.catalog.model.Product;
import com.budiluhur.catalog.repository.ProductRepository;
import com.budiluhur.catalog.exception.ProductNotFoundException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    private ProductRepository productRepository;

    @Override
    public void init() {
        // inisialisasi
        productRepository = new ProductRepository();

        productRepository.addProduct(new Product(
            "PRD-01", "Keyboard Mechanical", 450000.0));

        productRepository.addProduct(new Product(
            "PRD-02", "Mouse Wireless Silent", 175000.0));

        productRepository.addProduct(new Product(
            "PRD-03", "Monitor Gaming 24 Inch", 2100000.0));
    }

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        String id = request.getParameter("id");

        if (id != null && !id.isEmpty()) {

            try {
                // Nyari produk berdasar ID
                Product product = productRepository.findById(id);

                // Tampil detail produk
                out.println("<html>");
                out.println("<head><title>Detail Produk</title></head>");
                out.println("<body>");

                out.println("<h2>=== DETAIL PRODUK ===</h2>");

                out.println("<table border='1' cellpadding='8'>");
                out.println("<tr>");
                out.println("<th>ID Produk</th>");
                out.println("<th>Nama Produk</th>");
                out.println("<th>Harga Satuan</th>");
                out.println("</tr>");

                out.println("<tr>");
                out.println("<td>" + product.getId() + "</td>");
                out.println("<td>" + product.getName() + "</td>");
                out.println("<td>Rp" + product.getPrice() + "</td>");
                out.println("</tr>");

                out.println("</table>");

                out.println("</body>");
                out.println("</html>");

            } catch (ProductNotFoundException e) {

                // Jika produk ga ketemu,404
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);

                // pesan error
                out.println("<html>");
                out.println("<head><title>Produk Tidak Ditemukan</title></head>");
                out.println("<body>");

                out.println("<h2 style='color:red;'>");
                out.println("Produk tidak ditemukan!");
                out.println("</h2>");

                out.println("<p style='color:red;'>");
                out.println(e.getMessage());
                out.println("</p>");

                out.println("</body>");
                out.println("</html>");
            }

        } else {

            // Gada tampilan per id
            List<Product> products =
                productRepository.findAll();

            out.println("<html>");
            out.println("<head><title>Katalog Produk Web</title></head>");
            out.println("<body>");

            out.println("<h2>=== DAFTAR KATALOG PRODUK (WEB) ===</h2>");

            out.println("<table border='1' cellpadding='8'>");
            out.println("<tr>");
            out.println("<th>ID Produk</th>");
            out.println("<th>Nama Produk</th>");
            out.println("<th>Harga Satuan</th>");
            out.println("</tr>");

            for (Product p : products) {

                out.println("<tr>");
                out.println("<td>" + p.getId() + "</td>");
                out.println("<td>" + p.getName() + "</td>");
                out.println("<td>Rp" + p.getPrice() + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}