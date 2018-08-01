package cv.com.imag;

import java.awt.RenderingHints;
import java.awt.image.renderable.ParameterBlock;
import java.io.IOException;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

/**
 * 
 * @author edson
 *
 */
public class TesteImagem {

	public static void main(String[] args) throws IOException {
		/*
		 * Carrega a imagem, n�o utiliza Java.io.*, isso � bom, pois n�o teremos
		 * que trabalhar com bytes, a api cuida disso.
		 */
		PlanarImage pi = JAI.create("fileload", "images/beauty.jpg");

		System.out.println("Largura = " + pi.getWidth());
		System.out.println("Altura = " + pi.getHeight());

		/*
		 * Calculo para o novo tamanho, pois o JAI trabalha com ponto flutuante
		 * como valor para o novo size.
		 */
		double newSize = ((double) 240) / ((double) pi.getWidth());

		/*
		 * Aqui s�o passadas a imagem que queremos aplicar o resize e o novo
		 * tamanho para ela.
		 */
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(pi);
		pb.add(newSize);
		pb.add(newSize);

		/*
		 * � selecionado o tipo de renderiza��o, no caso, esta far� a
		 * interpola��o da imagem. Que no caso far� um resize com um �timo
		 * efeito, como se tivesse feito num editor de imagens(Gimp ou
		 * Photoshop).
		 */
		RenderingHints qualityHints = new RenderingHints(
				RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_RENDER_QUALITY);

		// � aplicado o efeito.
		pi = JAI.create("SubsampleAverage", pb, qualityHints);

		/*
		 * Como j� foi utilizado o ParameterBlock, tem que apontar para um novo
		 * objeto
		 */
		pb = new ParameterBlock();

		// � informado o local e o nome da nova imagem e o formato.
		pb.addSource(pi);
		pb.add("images/test_resize.jpg");
		pb.add("jpeg");
		JAI.create("filestore", pb);

		// Cria a imagem em disco redimencionada.
		pi = JAI.create("fileload", "images/test_resize.jpg");

		System.out.println("Largura = " + pi.getWidth());
		System.out.println("Altura = " + pi.getHeight());

	}

}