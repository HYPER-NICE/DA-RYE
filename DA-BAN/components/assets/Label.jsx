'use client';

import React from 'react';
import PropTypes from 'prop-types';

/**
 * Label 컴포넌트
 *
 * @param {React.ReactNode} children - Label에 표시될 내용
 * @param {string} htmlFor - 연관된 Input의 ID
 * @param {'normal' | 'inverse' | 'neutral' | 'disabled'} variant - Label 스타일 변형
 * @param {string} className - 추가 Tailwind 클래스
 * @returns {JSX.Element}
 */
export function Label({ children, htmlFor, variant = 'normal', className = '' }) {
	const baseStyle = 'block text-sm font-medium';
	const variantStyles = {
		normal: 'text-semantic-labelText-normal',
		inverse: 'text-semantic-labelText-inverse',
		neutral: 'text-semantic-labelText-neutral',
		disabled: 'text-semantic-labelText-disabled cursor-not-allowed',
	};

	return (
			<label
					htmlFor={htmlFor}
					className={`${baseStyle} ${variantStyles[variant]} ${className}`}
			>
				{children}
			</label>
	);
}

Label.propTypes = {
	children: PropTypes.node.isRequired,
	htmlFor: PropTypes.string,
	variant: PropTypes.oneOf(['normal', 'inverse', 'neutral', 'disabled']),
	className: PropTypes.string,
};

Label.defaultProps = {
	htmlFor: '',
	variant: 'normal',
	className: '',
};
